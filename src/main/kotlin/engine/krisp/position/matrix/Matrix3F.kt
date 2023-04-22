package engine.krisp.position.matrix

import engine.krisp.position.Vector3F
import java.nio.FloatBuffer

/**
 * Represents an immutable 3x3 matrix of floats. GLSL equivalent of mat3.
 */
class Matrix3F(
    private var m00: Float = 1.0f,
    private var m01: Float = 0.0f,
    private var m02: Float = 0.0f,
    private var m10: Float = 0.0f,
    private var m11: Float = 1.0f,
    private var m12: Float = 0.0f,
    private var m20: Float = 0.0f,
    private var m21: Float = 0.0f,
    private var m22: Float = 1.0f
) {

    constructor(column1: Vector3F, column2: Vector3F, column3: Vector3F) : this(
        column1.x, column2.x, column3.x,
        column1.y, column2.y, column3.y,
        column1.z, column2.z, column3.z
    )

    fun add(other: Matrix3F): Matrix3F {
        val result = Matrix3F()

        result.m00 = m00 + other.m00
        result.m10 = m10 + other.m10
        result.m20 = m20 + other.m20

        result.m01 = m01 + other.m01
        result.m11 = m11 + other.m11
        result.m21 = m21 + other.m21

        result.m02 = m02 + other.m02
        result.m12 = m12 + other.m12
        result.m22 = m22 + other.m22

        return result
    }

    fun subtract(other: Matrix3F): Matrix3F {
        return add(other.negate())
    }

    fun negate(): Matrix3F {
        return multiply(-1.0f)
    }

    fun multiply(other: Matrix3F): Matrix3F {
        val result = Matrix3F()
        result.m00 = m00 * other.m00 + m01 * other.m10 + m02 * other.m20
        result.m10 = m10 * other.m00 + m11 * other.m10 + m12 * other.m20
        result.m20 = m20 * other.m00 + m21 * other.m10 + m22 * other.m20
        result.m01 = m00 * other.m01 + m01 * other.m11 + m02 * other.m21
        result.m11 = m10 * other.m01 + m11 * other.m11 + m12 * other.m21
        result.m21 = m20 * other.m01 + m21 * other.m11 + m22 * other.m21
        result.m02 = m00 * other.m02 + m01 * other.m12 + m02 * other.m22
        result.m12 = m10 * other.m02 + m11 * other.m12 + m12 * other.m22
        result.m22 = m20 * other.m02 + m21 * other.m12 + m22 * other.m22
        return result
    }

    fun multiply(vector: Vector3F): Vector3F {
        val x: Float = m00 * vector.x + m01 * vector.y + m02 * vector.z
        val y: Float = m10 * vector.x + m11 * vector.y + m12 * vector.z
        val z: Float = m20 * vector.x + m21 * vector.y + m22 * vector.z
        return Vector3F(x, y, z)
    }

    fun multiply(scalar: Float): Matrix3F {
        val result = Matrix3F()

        result.m00 = this.m00 * scalar;
        result.m10 = this.m10 * scalar;
        result.m20 = this.m20 * scalar;

        result.m01 = this.m01 * scalar;
        result.m11 = this.m11 * scalar;
        result.m21 = this.m21 * scalar;

        result.m02 = this.m02 * scalar;
        result.m12 = this.m12 * scalar;
        result.m22 = this.m22 * scalar;

        return result;
    }

    fun transpose(): Matrix3F {
        val result = Matrix3F()

        result.m00 = this.m00
        result.m10 = this.m01
        result.m20 = this.m02

        result.m01 = this.m10
        result.m11 = this.m11
        result.m21 = this.m12

        result.m02 = this.m20
        result.m12 = this.m21
        result.m22 = this.m22

        return result
    }

    fun determinant(): Float {
        return m00 * m11 * m22 + m01 * m12 * m20 + m02 * m10 * m21 - m02 * m11 * m20 - m01 * m10 * m22 - m00 * m12 * m21
    }

    fun storeInBuffer(buffer: FloatBuffer) {
        buffer.put(m00)
            .put(m10)
            .put(m20)
            .put(m01)
            .put(m11)
            .put(m21)
            .put(m02)
            .put(m12)
            .put(m22)
        buffer.flip()
    }

}