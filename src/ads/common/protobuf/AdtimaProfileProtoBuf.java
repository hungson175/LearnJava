// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: adtima_profile.proto

package ads.common.protobuf;

public final class AdtimaProfileProtoBuf {
    private AdtimaProfileProtoBuf() {}
    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
    }
    public interface AdtimaProfileOrBuilder extends
            // @@protoc_insertion_point(interface_extends:ads.common.protobuf.AdtimaProfile)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>required uint32 gender = 1;</code>
         */
        boolean hasGender();
        /**
         * <code>required uint32 gender = 1;</code>
         */
        int getGender();

        /**
         * <code>required uint32 dob = 2;</code>
         */
        boolean hasDob();
        /**
         * <code>required uint32 dob = 2;</code>
         */
        int getDob();

        /**
         * <code>required uint64 time = 3;</code>
         */
        boolean hasTime();
        /**
         * <code>required uint64 time = 3;</code>
         */
        long getTime();

        /**
         * <code>optional uint64 mqinfo = 4;</code>
         */
        boolean hasMqinfo();
        /**
         * <code>optional uint64 mqinfo = 4;</code>
         */
        long getMqinfo();
    }
    /**
     * Protobuf type {@code ads.common.protobuf.AdtimaProfile}
     */
    public static final class AdtimaProfile extends
            com.google.protobuf.GeneratedMessage implements
            // @@protoc_insertion_point(message_implements:ads.common.protobuf.AdtimaProfile)
            AdtimaProfileOrBuilder {
        // Use AdtimaProfile.newBuilder() to construct.
        private AdtimaProfile(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }
        private AdtimaProfile(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

        private static final AdtimaProfile defaultInstance;
        public static AdtimaProfile getDefaultInstance() {
            return defaultInstance;
        }

        public AdtimaProfile getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.UnknownFieldSet unknownFields;
        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }
        private AdtimaProfile(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(input, unknownFields,
                                    extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 8: {
                            bitField0_ |= 0x00000001;
                            gender_ = input.readUInt32();
                            break;
                        }
                        case 16: {
                            bitField0_ |= 0x00000002;
                            dob_ = input.readUInt32();
                            break;
                        }
                        case 24: {
                            bitField0_ |= 0x00000004;
                            time_ = input.readUInt64();
                            break;
                        }
                        case 32: {
                            bitField0_ |= 0x00000008;
                            mqinfo_ = input.readUInt64();
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return ads.common.protobuf.AdtimaProfileProtoBuf.internal_static_ads_common_protobuf_AdtimaProfile_descriptor;
        }

        protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return ads.common.protobuf.AdtimaProfileProtoBuf.internal_static_ads_common_protobuf_AdtimaProfile_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile.class, ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile.Builder.class);
        }

        public static com.google.protobuf.Parser<AdtimaProfile> PARSER =
                new com.google.protobuf.AbstractParser<AdtimaProfile>() {
                    public AdtimaProfile parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new AdtimaProfile(input, extensionRegistry);
                    }
                };

        @java.lang.Override
        public com.google.protobuf.Parser<AdtimaProfile> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        public static final int GENDER_FIELD_NUMBER = 1;
        private int gender_;
        /**
         * <code>required uint32 gender = 1;</code>
         */
        public boolean hasGender() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }
        /**
         * <code>required uint32 gender = 1;</code>
         */
        public int getGender() {
            return gender_;
        }

        public static final int DOB_FIELD_NUMBER = 2;
        private int dob_;
        /**
         * <code>required uint32 dob = 2;</code>
         */
        public boolean hasDob() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }
        /**
         * <code>required uint32 dob = 2;</code>
         */
        public int getDob() {
            return dob_;
        }

        public static final int TIME_FIELD_NUMBER = 3;
        private long time_;
        /**
         * <code>required uint64 time = 3;</code>
         */
        public boolean hasTime() {
            return ((bitField0_ & 0x00000004) == 0x00000004);
        }
        /**
         * <code>required uint64 time = 3;</code>
         */
        public long getTime() {
            return time_;
        }

        public static final int MQINFO_FIELD_NUMBER = 4;
        private long mqinfo_;
        /**
         * <code>optional uint64 mqinfo = 4;</code>
         */
        public boolean hasMqinfo() {
            return ((bitField0_ & 0x00000008) == 0x00000008);
        }
        /**
         * <code>optional uint64 mqinfo = 4;</code>
         */
        public long getMqinfo() {
            return mqinfo_;
        }

        private void initFields() {
            gender_ = 0;
            dob_ = 0;
            time_ = 0L;
            mqinfo_ = 0L;
        }
        private byte memoizedIsInitialized = -1;
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            if (!hasGender()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasDob()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasTime()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            getSerializedSize();
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeUInt32(1, gender_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                output.writeUInt32(2, dob_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                output.writeUInt64(3, time_);
            }
            if (((bitField0_ & 0x00000008) == 0x00000008)) {
                output.writeUInt64(4, mqinfo_);
            }
            getUnknownFields().writeTo(output);
        }

        private int memoizedSerializedSize = -1;
        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeUInt32Size(1, gender_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeUInt32Size(2, dob_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeUInt64Size(3, time_);
            }
            if (((bitField0_ & 0x00000008) == 0x00000008)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeUInt64Size(4, mqinfo_);
            }
            size += getUnknownFields().getSerializedSize();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;
        @java.lang.Override
        protected java.lang.Object writeReplace()
                throws java.io.ObjectStreamException {
            return super.writeReplace();
        }

        public static ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }
        public static ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }
        public static ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }
        public static ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() { return Builder.create(); }
        public Builder newBuilderForType() { return newBuilder(); }
        public static Builder newBuilder(ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile prototype) {
            return newBuilder().mergeFrom(prototype);
        }
        public Builder toBuilder() { return newBuilder(this); }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }
        /**
         * Protobuf type {@code ads.common.protobuf.AdtimaProfile}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessage.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:ads.common.protobuf.AdtimaProfile)
                ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfileOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return ads.common.protobuf.AdtimaProfileProtoBuf.internal_static_ads_common_protobuf_AdtimaProfile_descriptor;
            }

            protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return ads.common.protobuf.AdtimaProfileProtoBuf.internal_static_ads_common_protobuf_AdtimaProfile_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile.class, ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile.Builder.class);
            }

            // Construct using ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }
            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
                }
            }
            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                gender_ = 0;
                bitField0_ = (bitField0_ & ~0x00000001);
                dob_ = 0;
                bitField0_ = (bitField0_ & ~0x00000002);
                time_ = 0L;
                bitField0_ = (bitField0_ & ~0x00000004);
                mqinfo_ = 0L;
                bitField0_ = (bitField0_ & ~0x00000008);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return ads.common.protobuf.AdtimaProfileProtoBuf.internal_static_ads_common_protobuf_AdtimaProfile_descriptor;
            }

            public ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile getDefaultInstanceForType() {
                return ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile.getDefaultInstance();
            }

            public ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile build() {
                ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile buildPartial() {
                ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile result = new ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.gender_ = gender_;
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.dob_ = dob_;
                if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
                    to_bitField0_ |= 0x00000004;
                }
                result.time_ = time_;
                if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
                    to_bitField0_ |= 0x00000008;
                }
                result.mqinfo_ = mqinfo_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile) {
                    return mergeFrom((ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile)other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile other) {
                if (other == ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile.getDefaultInstance()) return this;
                if (other.hasGender()) {
                    setGender(other.getGender());
                }
                if (other.hasDob()) {
                    setDob(other.getDob());
                }
                if (other.hasTime()) {
                    setTime(other.getTime());
                }
                if (other.hasMqinfo()) {
                    setMqinfo(other.getMqinfo());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!hasGender()) {

                    return false;
                }
                if (!hasDob()) {

                    return false;
                }
                if (!hasTime()) {

                    return false;
                }
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            private int bitField0_;

            private int gender_ ;
            /**
             * <code>required uint32 gender = 1;</code>
             */
            public boolean hasGender() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }
            /**
             * <code>required uint32 gender = 1;</code>
             */
            public int getGender() {
                return gender_;
            }
            /**
             * <code>required uint32 gender = 1;</code>
             */
            public Builder setGender(int value) {
                bitField0_ |= 0x00000001;
                gender_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required uint32 gender = 1;</code>
             */
            public Builder clearGender() {
                bitField0_ = (bitField0_ & ~0x00000001);
                gender_ = 0;
                onChanged();
                return this;
            }

            private int dob_ ;
            /**
             * <code>required uint32 dob = 2;</code>
             */
            public boolean hasDob() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }
            /**
             * <code>required uint32 dob = 2;</code>
             */
            public int getDob() {
                return dob_;
            }
            /**
             * <code>required uint32 dob = 2;</code>
             */
            public Builder setDob(int value) {
                bitField0_ |= 0x00000002;
                dob_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required uint32 dob = 2;</code>
             */
            public Builder clearDob() {
                bitField0_ = (bitField0_ & ~0x00000002);
                dob_ = 0;
                onChanged();
                return this;
            }

            private long time_ ;
            /**
             * <code>required uint64 time = 3;</code>
             */
            public boolean hasTime() {
                return ((bitField0_ & 0x00000004) == 0x00000004);
            }
            /**
             * <code>required uint64 time = 3;</code>
             */
            public long getTime() {
                return time_;
            }
            /**
             * <code>required uint64 time = 3;</code>
             */
            public Builder setTime(long value) {
                bitField0_ |= 0x00000004;
                time_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required uint64 time = 3;</code>
             */
            public Builder clearTime() {
                bitField0_ = (bitField0_ & ~0x00000004);
                time_ = 0L;
                onChanged();
                return this;
            }

            private long mqinfo_ ;
            /**
             * <code>optional uint64 mqinfo = 4;</code>
             */
            public boolean hasMqinfo() {
                return ((bitField0_ & 0x00000008) == 0x00000008);
            }
            /**
             * <code>optional uint64 mqinfo = 4;</code>
             */
            public long getMqinfo() {
                return mqinfo_;
            }
            /**
             * <code>optional uint64 mqinfo = 4;</code>
             */
            public Builder setMqinfo(long value) {
                bitField0_ |= 0x00000008;
                mqinfo_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>optional uint64 mqinfo = 4;</code>
             */
            public Builder clearMqinfo() {
                bitField0_ = (bitField0_ & ~0x00000008);
                mqinfo_ = 0L;
                onChanged();
                return this;
            }

            // @@protoc_insertion_point(builder_scope:ads.common.protobuf.AdtimaProfile)
        }

        static {
            defaultInstance = new AdtimaProfile(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:ads.common.protobuf.AdtimaProfile)
    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_ads_common_protobuf_AdtimaProfile_descriptor;
    private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internal_static_ads_common_protobuf_AdtimaProfile_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }
    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;
    static {
        java.lang.String[] descriptorData = {
                "\n\024adtima_profile.proto\022\023ads.common.proto" +
                        "buf\"J\n\rAdtimaProfile\022\016\n\006gender\030\001 \002(\r\022\013\n\003" +
                        "dob\030\002 \002(\r\022\014\n\004time\030\003 \002(\004\022\016\n\006mqinfo\030\004 \001(\004B" +
                        ",\n\023ads.common.protobufB\025AdtimaProfilePro" +
                        "toBuf"
        };
        com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
                    public com.google.protobuf.ExtensionRegistry assignDescriptors(
                            com.google.protobuf.Descriptors.FileDescriptor root) {
                        descriptor = root;
                        return null;
                    }
                };
        com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[] {
                        }, assigner);
        internal_static_ads_common_protobuf_AdtimaProfile_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_ads_common_protobuf_AdtimaProfile_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                internal_static_ads_common_protobuf_AdtimaProfile_descriptor,
                new java.lang.String[] { "Gender", "Dob", "Time", "Mqinfo", });
    }

    // @@protoc_insertion_point(outer_class_scope)
}