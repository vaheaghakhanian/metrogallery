package com.ramonapp.metgallery.data.api

import com.ramonapp.metgallery.domain.model.DataResult
import com.ramonapp.metgallery.domain.model.ErrorType
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class DefaultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType =
                getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                DataResult::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ResultAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}

class ResultAdapter(
    private val type: Type
) : CallAdapter<Type, Call<DataResult<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<DataResult<Type>> = ResultCall(call)
}


/**
 * In this class we can check the raw result
 * of api call and do some low level modification.
 */
class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, DataResult<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<DataResult<T>>) =
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {

                val result = when (response.code()) {
                    in 200..299 -> DataResult.Success(response.body())
                    in 400..499 -> DataResult.Failure(ErrorType.ClientError)
                    in 500..599 -> DataResult.Failure(ErrorType.ServerError)
                    else -> DataResult.Failure(ErrorType.UnExpectedError)
                }

                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = if (t is IOException) {
                    DataResult.Failure(ErrorType.NetworkError)
                } else {
                    DataResult.Failure(ErrorType.UnExpectedError)
                }

                callback.onResponse(this@ResultCall, Response.success(result))
            }
        })

    override fun cloneImpl() = ResultCall(proxy.clone())
}

abstract class CallDelegate<TIn, TOut>(
    protected val proxy: Call<TIn>
) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled
    override fun timeout(): Timeout = proxy.timeout()

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
}


