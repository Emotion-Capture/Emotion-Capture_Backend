// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: common/common.proto

package com.emocap.commonproto;

public final class CommonProto {
  private CommonProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_common_JWTRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_common_JWTRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_common_JWTResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_common_JWTResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023common/common.proto\022\006common\"\033\n\nJWTRequ" +
      "est\022\r\n\005token\030\001 \001(\t\"\036\n\013JWTResponse\022\017\n\007suc" +
      "cess\030\001 \001(\0102P\n\022CommonProtoService\022:\n\rvali" +
      "dateToken\022\022.common.JWTRequest\032\023.common.J" +
      "WTResponse\"\000B\'\n\026com.emocap.commonprotoB\013" +
      "CommonProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_common_JWTRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_common_JWTRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_common_JWTRequest_descriptor,
        new java.lang.String[] { "Token", });
    internal_static_common_JWTResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_common_JWTResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_common_JWTResponse_descriptor,
        new java.lang.String[] { "Success", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
