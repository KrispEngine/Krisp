package engine.krisp.position.matrix

import engine.krisp.position.Vector2F
import java.nio.FloatBuffer


/**
 * Represents an immutable 2x2 matrix of floats. GLSL equivalent of mat2.
 */
class Matrix2F(
    private var m00: Float = 1.0f,
    private var m01: Float = 0.0f,
    private var m10: Float = 0.0f,
    private var m11: Float = 1.0f
) {


    constructor(column1: Vector2F, column2: Vector2F) : this(
        column1.x, column2.x,
        column1.y, column2.y
    )

    fun add(other: Matrix2F): Matrix2F {
        val result = Matrix2F()

        result.m00 = this.m00 + other.m00
        result.m10 = this.m10 + other.m10

        result.m01 = this.m01 + other.m01
        result.m11 = this.m11 + other.m11

        return result
    }

    fun multiply(scalar: Float): Matrix2F {
        val result = Matrix2F()

        result.m00 = this.m00 * scalar
        result.m10 = this.m10 * scalar

        result.m01 = this.m01 * scalar
        result.m11 = this.m11 * scalar

        return result
    }

    fun multiply(vector2F: Vector2F): Vector2F {
        val x: Float = this.m00 * vector2F.x + this.m01 * vector2F.y
        val y: Float = this.m10 * vector2F.x + this.m11 * vector2F.y
        return Vector2F(x, y)
    }

    fun multiply(other: Matrix2F): Matrix2F {
        val result = Matrix2F()

        result.m00 = this.m00 * other.m00 + this.m01 * other.m10
        result.m10 = this.m10 * other.m00 + this.m11 * other.m10

        result.m01 = this.m00 * other.m01 + this.m01 * other.m11
        result.m11 = this.m10 * other.m01 + this.m11 * other.m11

        return result
    }

    fun negate(): Matrix2F {
        return multiply(-1.0f)
    }

    fun subtract(other: Matrix2F): Matrix2F {
        return add(other.negate()) // a - b = a + (-b)
    }

    fun transpose(): Matrix2F {
        val result = Matrix2F()

        result.m00 = this.m00
        result.m10 = this.m01

        result.m01 = this.m10
        result.m11 = this.m11

        return result
    }

    fun determinant(): Float {
        return m00 * m11 - m01 * m10
    }

    fun storeInBuffer(buffer: FloatBuffer) {
        buffer.put(m00).put(m10).put(m01).put(m11)
        buffer.flip()
    }

}