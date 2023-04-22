package engine.krisp.position

import java.nio.FloatBuffer
import kotlin.math.sqrt

/**
 * Represents an immutable point in 3D space. glsl vec3 equivalent.
 */
class Vector3F(
    val x: Float,
    val y: Float,
    val z: Float
) {


    companion object {
        val ZERO = Vector3F(0.0f, 0.0f, 0.0f)
    }

    constructor() : this(0.0f, 0.0f, 0.0f)
    constructor(x: Int, y: Int, z: Int) : this(x.toFloat(), y.toFloat(), z.toFloat())
    constructor(x: Double, y: Double, z: Double) : this(x.toFloat(), y.toFloat(), z.toFloat())

    fun lengthSquared(): Float = x * x + y * y + z * z

    fun length(): Float = sqrt(lengthSquared())

    fun add(other: Vector3F): Vector3F = Vector3F(x + other.x, y + other.y, z + other.z)

    fun add(x: Float, y: Float, z: Float): Vector3F = Vector3F(this.x + x, this.y + y, this.z + z)

    fun subtract(other: Vector3F): Vector3F = Vector3F(x - other.x, y - other.y, z - other.z)

    fun subtract(x: Float, y: Float, z: Float): Vector3F = Vector3F(this.x - x, this.y - y, this.z - z)

    fun multiply(other: Vector3F): Vector3F = Vector3F(x * other.x, y * other.y, z * other.z)

    fun multiply(x: Float, y: Float, z: Float): Vector3F = Vector3F(this.x * x, this.y * y, this.z * z)

    fun divide(other: Vector3F): Vector3F = Vector3F(x / other.x, y / other.y, z / other.z)

    fun divide(x: Float, y: Float, z: Float): Vector3F = Vector3F(this.x / x, this.y / y, this.z / z)

    fun scale(scalar: Float): Vector3F = Vector3F(x * scalar, y * scalar, z * scalar)

    fun scale(scalar: Double): Vector3F = Vector3F(x * scalar.toFloat(), y * scalar.toFloat(), z * scalar.toFloat())

    fun scale(scalar: Int): Vector3F = Vector3F(x * scalar, y * scalar, z * scalar)

    fun normalize(): Vector3F {
        val length = length()
        return Vector3F(x / length, y / length, z / length)
    }

    fun negate(): Vector3F = scale(-1.0f)

    fun dot(other: Vector3F): Float = x * other.x + y * other.y + z * other.z

    fun cross(other: Vector3F): Vector3F = Vector3F(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
    )

    fun lerp(other: Vector3F, alpha: Float): Vector3F = Vector3F(
        x + (other.x - x) * alpha,
        y + (other.y - y) * alpha,
        z + (other.z - z) * alpha
    )

    fun storeInBuffer(buffer: FloatBuffer) {
        buffer.put(x).put(y).put(z)
        buffer.flip()
    }


}