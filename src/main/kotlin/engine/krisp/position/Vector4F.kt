package engine.krisp.position

import java.nio.FloatBuffer
import kotlin.math.sqrt

/**
 * Represents an immutable 4D vector. glsl vec4 equivalent.
 */
class Vector4F(
    val x: Float,
    val y: Float,
    val z: Float,
    val w: Float // w is the fourth dimension. It is used for homogeneous coordinates.
) {

    companion object {
        val ZERO = Vector4F(0.0f, 0.0f, 0.0f, 0.0f)
    }

    constructor() : this(0.0f, 0.0f, 0.0f, 0.0f)
    constructor(x: Int, y: Int, z: Int, w: Int) : this(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())
    constructor(x: Double, y: Double, z: Double, w: Double) : this(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())

    fun lengthSquared(): Float = x * x + y * y + z * z + w * w

    fun length(): Float = sqrt(lengthSquared())

    fun add(other: Vector4F): Vector4F = Vector4F(x + other.x, y + other.y, z + other.z, w + other.w)

    fun add(x: Float, y: Float, z: Float, w: Float): Vector4F = Vector4F(this.x + x, this.y + y, this.z + z, this.w + w)

    fun subtract(other: Vector4F): Vector4F = Vector4F(x - other.x, y - other.y, z - other.z, w - other.w)

    fun subtract(x: Float, y: Float, z: Float, w: Float): Vector4F = Vector4F(this.x - x, this.y - y, this.z - z, this.w - w)

    fun multiply(other: Vector4F): Vector4F = Vector4F(x * other.x, y * other.y, z * other.z, w * other.w)

    fun multiply(x: Float, y: Float, z: Float, w: Float): Vector4F = Vector4F(this.x * x, this.y * y, this.z * z, this.w * w)

    fun divide(other: Vector4F): Vector4F = Vector4F(x / other.x, y / other.y, z / other.z, w / other.w)

    fun divide(x: Float, y: Float, z: Float, w: Float): Vector4F = Vector4F(this.x / x, this.y / y, this.z / z, this.w / w)

    fun scale(scalar: Float): Vector4F = Vector4F(x * scalar, y * scalar, z * scalar, w * scalar)

    fun scale(scalar: Double): Vector4F = Vector4F(x * scalar.toFloat(), y * scalar.toFloat(), z * scalar.toFloat(), w * scalar.toFloat())

    fun scale(scalar: Int): Vector4F = Vector4F(x * scalar, y * scalar, z * scalar, w * scalar)

    fun normalize(): Vector4F {
        val length = length()
        return Vector4F(x / length, y / length, z / length, w / length)
    }

    fun dot(other: Vector4F): Float = x * other.x + y * other.y + z * other.z + w * other.w

    fun cross(other: Vector4F): Vector4F {
        val x = y * other.z - z * other.y
        val y = z * other.x - x * other.z
        val z = x * other.y - y * other.x
        return Vector4F(x, y, z, 0.0f)
    }

    fun lerp(other: Vector4F, alpha: Float): Vector4F {
        val x = x + (other.x - x) * alpha
        val y = y + (other.y - y) * alpha
        val z = z + (other.z - z) * alpha
        val w = w + (other.w - w) * alpha
        return Vector4F(x, y, z, w)
    }

    fun storeInBuffer(buffer: FloatBuffer) {
        buffer.put(x).put(y).put(z).put(w)
        buffer.flip()
    }

}