package com.readnumber.dailyonestep.common.util

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.springframework.core.io.ClassPathResource
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class SeedDataLoadHelper {
    companion object {
        const val patterDateTime = "yyyy-MM-dd HH:mm:ss.SSS"
        const val patternDate = "yyyy-MM-dd"
        const val patternTime = "HH:mm:ss.SSS"
        inline fun <reified T : Any> loadData(filename: String): T {
            val resource = ClassPathResource("seed/$filename")
            val jsonString = String(resource.inputStream.readBytes())
            val gson = GsonBuilder()
                .setDateFormat(patterDateTime)
                .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter().nullSafe())
                .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter().nullSafe())
                .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter().nullSafe())
                .create()

            return gson.fromJson(jsonString, T::class.java)
        }

        class LocalDateTimeAdapter : TypeAdapter<LocalDateTime?>() {
            private var format: DateTimeFormatter = DateTimeFormatter.ofPattern(patterDateTime)
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: LocalDateTime?) {
                if (value != null) out.value(value.format(format))
            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): LocalDateTime {
                return LocalDateTime.parse(`in`.nextString(), format)
            }
        }

        class LocalDateAdapter : TypeAdapter<LocalDate?>() {
            private var format: DateTimeFormatter = DateTimeFormatter.ofPattern(patternDate)
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: LocalDate?) {
                if (value != null) out.value(value.format(format))
            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): LocalDate {
                return LocalDate.parse(`in`.nextString(), format)
            }
        }

        class LocalTimeAdapter : TypeAdapter<LocalTime?>() {
            var format: DateTimeFormatter = DateTimeFormatter.ofPattern(patternTime)
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: LocalTime?) {
                if (value != null) out.value(value.format(format))
            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): LocalTime {
                return LocalTime.parse(`in`.nextString(), format)
            }
        }
    }
}