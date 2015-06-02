package com.test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * A {@link Converter} which uses Jackson for reading and writing entities.
 *
 * @author Kai Waldron (kaiwaldron@gmail.com)
 */
public class JacksonConverter implements Converter {
  private final ObjectMapper objectMapper;
  private String charset;

  /**
   * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON and
   * decoding from JSON (when no charset is specified by a header) will use UTF-8.
   */
  public JacksonConverter(ObjectMapper objectMapper) {
    this(objectMapper, "UTF-8");
  }

  /**
   * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON and
   * decoding from JSON (when no charset is specified by a header) will use the specified charset.
   */
  public JacksonConverter(ObjectMapper objectMapper, String charset) {
    this.objectMapper = objectMapper;
    this.charset = charset;
  }

  @Override public Object fromBody(TypedInput body, Type type) throws ConversionException {
    String charset = this.charset;
    if (body.mimeType() != null) {
      charset = MimeUtil.parseCharset(body.mimeType(), charset);
    }
    InputStreamReader isr = null;
    try {
      isr = new InputStreamReader(body.in(), charset);
      JavaType javaType = objectMapper.getTypeFactory().constructType(type);
      return objectMapper.readValue(isr, javaType);
    } catch (IOException e) {
      throw new ConversionException(e);
    } catch (JsonParseException e) {
      throw new ConversionException(e);
    } finally {
      if (isr != null) {
        try {
          isr.close();
        } catch (IOException ignored) {
        }
      }
    }
  }

  @Override public TypedOutput toBody(Object object) {
      try {
       String tob =  objectMapper.readValue(String.valueOf(object), String.class);
        return new JsonTypedOutput(tob.getBytes(charset), charset);
      } catch (IOException e) {
        throw new AssertionError(e);
      }
  }

  private static class JsonTypedOutput implements TypedOutput {
    private final byte[] jsonBytes;
    private final String mimeType;

    JsonTypedOutput(byte[] jsonBytes, String encode) {
      this.jsonBytes = jsonBytes;
      this.mimeType = "application/json; charset=" + encode;
    }

    @Override public String fileName() {
      return null;
    }

    @Override public String mimeType() {
      return mimeType;
    }

    @Override public long length() {
      return jsonBytes.length;
    }

    @Override public void writeTo(OutputStream out) throws IOException {
      out.write(jsonBytes);
    }
  }
}
