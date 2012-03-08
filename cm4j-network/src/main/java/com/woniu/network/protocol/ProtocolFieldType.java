package com.woniu.network.protocol;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.util.CharsetUtil;

import com.woniu.network.util.CodecUtil;

/**
 * 协议字段类型
 * 
 * @author yang.hao
 * @since 2011-10-26 下午2:07:57
 */
public abstract class ProtocolFieldType<T> {

	public static final int BYTE_VALUE = 0;
	public static final int INT_VALUE = 2;
	public static final int STRING_VALUE = 4;
	public static final int WSTRING_VALUE = 5;
	public static final int LONG_VALUE = 9;
	public static final int DATE_VALUE = 10;

	/**
	 * 类型对应的编码值(与计费应用协商)
	 */
	private int type;
	/**
	 * 名称(与配置一致)
	 */
	private String name;

	public ProtocolFieldType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	/**
	 * 返回默认值
	 * 
	 * @return
	 */
	public abstract T defaultValue();

	/**
	 * 放入buffer
	 * 
	 * @param buffer
	 * @param value
	 * @throws IndexOutOfBoundsException
	 */
	public abstract void put(ChannelBuffer buffer, T value);

	/**
	 * 从buffer中获取值
	 * 
	 * @param buffer
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public abstract T get(ChannelBuffer buffer);

	/**
	 * 将String类型转化为本类型所代表的值
	 * 
	 * @param value
	 * @throws NumberFormatException
	 */
	public abstract T decode(String value);

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static final ProtocolFieldType<Byte> ByteType = new ProtocolFieldType<Byte>(BYTE_VALUE, "byte") {
		@Override
		public Byte defaultValue() {
			return 0;
		}

		@Override
		public void put(ChannelBuffer buffer, Byte value) {
			buffer.writeByte(value);
		}

		@Override
		public Byte get(ChannelBuffer buffer) {
			return buffer.readByte();
		};

		@Override
		public Byte decode(String value) {
			return Byte.decode(value);
		};
	};

	public static final ProtocolFieldType<Integer> IntType = new ProtocolFieldType<Integer>(INT_VALUE, "int") {
		@Override
		public Integer defaultValue() {
			return 0;
		}

		@Override
		public void put(ChannelBuffer buffer, Integer value) {
			buffer.writeInt(value);
		}

		@Override
		public Integer get(ChannelBuffer buffer) {
			return buffer.readInt();
		};

		@Override
		public Integer decode(String value) {
			return Integer.decode(value);
		};

	};

	public static final ProtocolFieldType<String> StringType = new ProtocolFieldType<String>(STRING_VALUE, "string") {
		@Override
		public String defaultValue() {
			return "";
		}

		@Override
		public void put(ChannelBuffer buffer, String value) {
			buffer.writeBytes(CodecUtil.encode(value, CharsetUtil.UTF_8));
		}

		@Override
		public String get(ChannelBuffer buffer) {
			return CodecUtil.decode(buffer, CharsetUtil.UTF_8);
		};

		@Override
		public String decode(String value) {
			return value;
		};
	};

	public static final ProtocolFieldType<String> WStringType = new ProtocolFieldType<String>(WSTRING_VALUE, "wstring") {
		@Override
		public String defaultValue() {
			return "";
		}

		@Override
		public void put(ChannelBuffer buffer, String value) {
			buffer.writeBytes(CodecUtil.encodeW(value, CharsetUtil.UTF_16));
		}

		@Override
		public String get(ChannelBuffer buffer) {
			return CodecUtil.decode(buffer, CharsetUtil.UTF_16);
		}

		@Override
		public String decode(String value) {
			return value;
		};

	};

	public static final ProtocolFieldType<Long> LongType = new ProtocolFieldType<Long>(LONG_VALUE, "long") {
		@Override
		public Long defaultValue() {
			return 0L;
		}

		@Override
		public void put(ChannelBuffer buffer, Long value) {
			buffer.writeLong(value);
		}

		@Override
		public Long get(ChannelBuffer buffer) {
			return buffer.readLong();
		};

		@Override
		public Long decode(String value) {
			return Long.decode(value);
		}
	};
	public static final ProtocolFieldType<Long> DateType = new ProtocolFieldType<Long>(DATE_VALUE, "date") {
		@Override
		public Long defaultValue() {
			return 0L;
		}

		@Override
		public void put(ChannelBuffer buffer, Long value) {
			buffer.writeLong(value / 1000);
		}

		@Override
		public Long get(ChannelBuffer buffer) {
			return buffer.readLong() * 1000;
		};

		@Override
		public Long decode(String value) {
			return Long.decode(value);
		};
	};

	@SuppressWarnings("rawtypes")
	public static ProtocolFieldType getType(Object value) {
		if (value instanceof Byte) {
			return ProtocolFieldType.ByteType;
		} else if (value instanceof Integer) {
			return ProtocolFieldType.IntType;
		} else if (value instanceof Character) {
			return ProtocolFieldType.StringType;
		} else if (value instanceof String) {
			return ProtocolFieldType.WStringType;
		} else if (value instanceof Long) {
			return ProtocolFieldType.LongType;
		} else {
			throw new UnsupportedOperationException("type " + value.getClass().getSimpleName() + " is un support");
		}
	}

	@SuppressWarnings("rawtypes")
	public static ProtocolFieldType getType(int type) {
		switch (type) {
		case BYTE_VALUE:
			return ByteType;
		case INT_VALUE:
			return IntType;
		case LONG_VALUE:
			return LongType;
		case STRING_VALUE:
			return StringType;
		case WSTRING_VALUE:
			return WStringType;
		case DATE_VALUE:
			return DateType;
		default:
			throw new UnsupportedOperationException("type " + type + " is un support");
		}
	}

	@SuppressWarnings("rawtypes")
	public static ProtocolFieldType getType(String name) {
		if (StringUtils.equals(name, ByteType.getName())) {
			return ByteType;
		} else if (StringUtils.equals(name, IntType.getName())) {
			return IntType;
		} else if (StringUtils.equals(name, LongType.getName())) {
			return LongType;
		} else if (StringUtils.equals(name, StringType.getName())) {
			return StringType;
		} else if (StringUtils.equals(name, WStringType.getName())) {
			return WStringType;
		} else if (StringUtils.equals(name, DateType.getName())) {
			return DateType;
		} else {
			throw new UnsupportedOperationException("type " + name + " is un support");
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
