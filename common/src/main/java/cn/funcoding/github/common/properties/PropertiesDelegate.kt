package cn.funcoding.github.common.properties

import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.net.URL
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSuperclassOf

/**
 * Properties属性代理
 */
class PropertiesDelegate(private val path: String) {
    private lateinit var url: URL

    private val properties: Properties by lazy {
        val prop = Properties()
        url = try {
            javaClass.getResourceAsStream(path).use {
                prop.load(it)
            }
            javaClass.getResource(path)
        } catch (e: Exception) {
            try {
                ClassLoader.getSystemClassLoader().getResourceAsStream(path).use {
                    prop.load(it)
                }
                ClassLoader.getSystemClassLoader().getResource(path)
            } catch (e: Exception) {
                FileInputStream(path).use {
                    prop.load(it)
                }
                URL("file://${File(path).canonicalPath}")
            }
        }
        prop
    }

    operator fun <T> getValue(thisRef: Any, property: KProperty<*>): T {
        val value = properties[property.name]
        val classOfT = property.returnType.classifier as KClass<*>
        return when {
            Boolean::class == classOfT -> value.toString().toBoolean()
            Number::class.isSuperclassOf(classOfT) -> {
                classOfT.javaObjectType.getDeclaredMethod(
                    "parse${classOfT.simpleName}",
                    String::class.java
                ).invoke(null, value)
            }
            String::class == classOfT -> value
            else -> throw IllegalArgumentException("不支持的类型")
        } as T
    }

    operator fun <T> setValue(thisRef: Any, property: KProperty<*>, value: T) {
        properties[property.name] = value.toString()
        File(url.toURI()).outputStream().use {
            properties.store(it, "")
        }
    }

}