// Generated by the protocol buffer compiler. DO NOT EDIT!
// source: common/common.proto

// Generated files should ignore deprecation warnings
@file:Suppress("DEPRECATION")
package com.emocap.commonproto;

@kotlin.jvm.JvmName("-initializejWTRequest")
public inline fun jWTRequest(block: com.emocap.commonproto.JWTRequestKt.Dsl.() -> kotlin.Unit): com.emocap.commonproto.JWTRequest =
  com.emocap.commonproto.JWTRequestKt.Dsl._create(com.emocap.commonproto.JWTRequest.newBuilder()).apply { block() }._build()
/**
 * Protobuf type `common.JWTRequest`
 */
public object JWTRequestKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: com.emocap.commonproto.JWTRequest.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: com.emocap.commonproto.JWTRequest.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): com.emocap.commonproto.JWTRequest = _builder.build()

    /**
     * `string token = 1;`
     */
    public var token: kotlin.String
      @JvmName("getToken")
      get() = _builder.getToken()
      @JvmName("setToken")
      set(value) {
        _builder.setToken(value)
      }
    /**
     * `string token = 1;`
     */
    public fun clearToken() {
      _builder.clearToken()
    }
  }
}
@kotlin.jvm.JvmSynthetic
public inline fun com.emocap.commonproto.JWTRequest.copy(block: com.emocap.commonproto.JWTRequestKt.Dsl.() -> kotlin.Unit): com.emocap.commonproto.JWTRequest =
  com.emocap.commonproto.JWTRequestKt.Dsl._create(this.toBuilder()).apply { block() }._build()

