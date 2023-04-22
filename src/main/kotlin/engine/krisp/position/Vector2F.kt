package engine.krisp.position

import java.nio.FloatBuffer
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Represents an immutable point in 2D space. glsl vec2 equivalent.
 */
class Vector2F(
    val x: Float,
    val y: Float
) {

    companion object {
        val ZERO = Vector2F(0.0f, 0.0f)
    }

    constructor() : this(0.0f, 0.0f)
    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())
    constructor(x: Double, y: Double) : this(x.toFloat(), y.toFloat())

    fun lengthSquared(): Float = x * x + y * y

    fun length(): Float = sqrt(lengthSquared())

    fun add(other: Vector2F): Vector2F = Vector2F(x + other.x, y + other.y)

    fun add(x: Float, y: Float): Vector2F = Vector2F(this.x + x, this.y + y)

    fun subtract(other: Vector2F): Vector2F = Vector2F(x - other.x, y - other.y)

    fun subtract(x: Float, y: Float): Vector2F = Vector2F(this.x - x, this.y - y)

    fun multiply(other: Vector2F): Vector2F = Vector2F(x * other.x, y * other.y)

    fun multiply(x: Float, y: Float): Vector2F = Vector2F(this.x * x, this.y * y)


    fun divide(other: Vector2F): Vector2F = Vector2F(x / other.x, y / other.y)

    fun divide(x: Float, y: Float): Vector2F = Vector2F(this.x / x, this.y / y)

    fun scale(scalar: Float): Vector2F = Vector2F(x * scalar, y * scalar)

    fun scale(scalar: Double): Vector2F = Vector2F(x * scalar.toFloat(), y * scalar.toFloat())

    fun scale(scalar: Int): Vector2F = Vector2F(x * scalar, y * scalar)

    fun normalize(): Vector2F {
        val length = length()
        return Vector2F(x / length, y / length)
    }

    fun negate(): Vector2F = scale(-1.0f)

    fun dot(other: Vector2F): Float = x * other.x + y * other.y

    fun cross(other: Vector2F): Float = x * other.y - y * other.x

    fun lerp(other: Vector2F, alpha: Float): Vector2F = add(other.subtract(this).scale(alpha))

    fun storeInBuffer(buffer: FloatBuffer) {
        buffer.put(x).put(y)
        buffer.flip() // Buffer is now ready to be read from
    }

}