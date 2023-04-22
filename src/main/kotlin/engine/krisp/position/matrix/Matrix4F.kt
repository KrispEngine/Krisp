package engine.krisp.position.matrix

import engine.krisp.position.Vector3F
import engine.krisp.position.Vector4F
import java.nio.FloatBuffer
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan


/**
 * Represents an immutable 4x4 matrix of floats. GLSL equivalent of mat4.
 */
class Matrix4F(
    private var m00: Float = 1.0f,
    private var m01: Float = 0.0f,
    private var m02: Float = 0.0f,
    private var m03: Float = 0.0f,
    private var m10: Float = 0.0f,
    private var m11: Float = 1.0f,
    private var m12: Float = 0.0f,
    private var m13: Float = 0.0f,
    private var m20: Float = 0.0f,
    private var m21: Float = 0.0f,
    private var m22: Float = 1.0f,
    private var m23: Float = 0.0f,
    private var m30: Float = 0.0f,
    private var m31: Float = 0.0f,
    private var m32: Float = 0.0f,
    private var m33: Float = 1.0f
) {


    constructor(column1: Vector4F, column2: Vector4F, column3: Vector4F, column4: Vector4F) : this(
        column1.x, column2.x, column3.x, column4.x,
        column1.y, column2.y, column3.y, column4.y,
        column1.z, column2.z, column3.z, column4.z,
        column1.w, column2.w, column3.w, column4.w
    )

    fun add(other: Matrix4F): Matrix4F {
        val result = Matrix4F()

        result.m00 = m00 + other.m00
        result.m10 = m10 + other.m10
        result.m20 = m20 + other.m20
        result.m30 = m30 + other.m30

        result.m01 = m01 + other.m01
        result.m11 = m11 + other.m11
        result.m21 = m21 + other.m21
        result.m31 = m31 + other.m31

        result.m02 = m02 + other.m02
        result.m12 = m12 + other.m12
        result.m22 = m22 + other.m22
        result.m32 = m32 + other.m32

        result.m03 = m03 + other.m03
        result.m13 = m13 + other.m13
        result.m23 = m23 + other.m23
        result.m33 = m33 + other.m33

        return result
    }

    fun multiply(vector: Vector4F): Vector4F {
        val x: Float = m00 * vector.x + m01 * vector.y + m02 * vector.z + m03 * vector.w
        val y: Float = m10 * vector.x + m11 * vector.y + m12 * vector.z + m13 * vector.w
        val z: Float = m20 * vector.x + m21 * vector.y + m22 * vector.z + m23 * vector.w
        val w: Float = m30 * vector.x + m31 * vector.y + m32 * vector.z + m33 * vector.w
        return Vector4F(x, y, z, w)
    }

    fun subtract(other: Matrix4F): Matrix4F {
        return this.add(other.negate())
    }

    fun negate(): Matrix4F {
        return this.multiply(-1.0f)
    }


    fun multiply(other: Matrix4F): Matrix4F {
        val result = Matrix4F()

        result.m00 = m00 * other.m00 + m01 * other.m10 + m02 * other.m20 + m03 * other.m30
        result.m10 = m10 * other.m00 + m11 * other.m10 + m12 * other.m20 + m13 * other.m30
        result.m20 = m20 * other.m00 + m21 * other.m10 + m22 * other.m20 + m23 * other.m30
        result.m30 = m30 * other.m00 + m31 * other.m10 + m32 * other.m20 + m33 * other.m30

        result.m01 = m00 * other.m01 + m01 * other.m11 + m02 * other.m21 + m03 * other.m31
        result.m11 = m10 * other.m01 + m11 * other.m11 + m12 * other.m21 + m13 * other.m31
        result.m21 = m20 * other.m01 + m21 * other.m11 + m22 * other.m21 + m23 * other.m31
        result.m31 = m30 * other.m01 + m31 * other.m11 + m32 * other.m21 + m33 * other.m31

        result.m02 = m00 * other.m02 + m01 * other.m12 + m02 * other.m22 + m03 * other.m32
        result.m12 = m10 * other.m02 + m11 * other.m12 + m12 * other.m22 + m13 * other.m32
        result.m22 = m20 * other.m02 + m21 * other.m12 + m22 * other.m22 + m23 * other.m32
        result.m32 = m30 * other.m02 + m31 * other.m12 + m32 * other.m22 + m33 * other.m32

        result.m03 = m00 * other.m03 + m01 * other.m13 + m02 * other.m23 + m03 * other.m33
        result.m13 = m10 * other.m03 + m11 * other.m13 + m12 * other.m23 + m13 * other.m33
        result.m23 = m20 * other.m03 + m21 * other.m13 + m22 * other.m23 + m23 * other.m33
        result.m33 = m30 * other.m03 + m31 * other.m13 + m32 * other.m23 + m33 * other.m33

        return result
    }

    fun multiply(scalar: Float): Matrix4F {
        val result = Matrix4F()

        result.m00 = m00 * scalar
        result.m10 = m10 * scalar
        result.m20 = m20 * scalar
        result.m30 = m30 * scalar

        result.m01 = m01 * scalar
        result.m11 = m11 * scalar
        result.m21 = m21 * scalar
        result.m31 = m31 * scalar

        result.m02 = m02 * scalar
        result.m12 = m12 * scalar
        result.m22 = m22 * scalar
        result.m32 = m32 * scalar

        result.m03 = m03 * scalar
        result.m13 = m13 * scalar
        result.m23 = m23 * scalar
        result.m33 = m33 * scalar

        return result
    }

    fun transpose(): Matrix4F {
        val result = Matrix4F()

        result.m00 = m00
        result.m10 = m01
        result.m20 = m02
        result.m30 = m03

        result.m01 = m10
        result.m11 = m11
        result.m21 = m12
        result.m31 = m13

        result.m02 = m20
        result.m12 = m21
        result.m22 = m22
        result.m32 = m23

        result.m03 = m30
        result.m13 = m31
        result.m23 = m32
        result.m33 = m33

        return result
    }

    fun storeInBuffer(buffer: FloatBuffer) {
        buffer.put(m00)
            .put(m10)
            .put(m20)
            .put(m01)
            .put(m11)
            .put(m31)
            .put(m02)
            .put(m12)
            .put(m22)
            .put(m32)
            .put(m03)
            .put(m13)
            .put(m23)
            .put(m33)

        buffer.flip()
    }

    /* Similar to the OpenGL function glOrtho */
    fun orthographic(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Matrix4F {
        val ortho = Matrix4F()

        val tx = -(right + left) / (right - left)
        val ty: Float = -(top + bottom) / (top - bottom)
        val tz = -(far + near) / (far - near)

        ortho.m00 = 2f / (right - left)
        ortho.m11 = 2f / (top - bottom)
        ortho.m22 = -2f / (far - near)
        ortho.m03 = tx
        ortho.m13 = ty
        ortho.m23 = tz

        return ortho
    }

    /* Similar to the OpenGL function glFrustum */
    fun frustum(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Matrix4F {
        val frustum = Matrix4F()

        val A = (right + left) / (right - left)
        val B = (top + bottom) / (top - bottom)
        val C = -(far + near) / (far - near)
        val D = -2f * far * near / (far - near)

        frustum.m00 = 2f * near / (right - left)
        frustum.m11 = 2f * near / (top - bottom)
        frustum.m02 = A
        frustum.m12 = B
        frustum.m22 = C
        frustum.m32 = -1f
        frustum.m23 = D

        return frustum
    }

    /* Similar to the OpenGL function gluPerspective */
    fun perspective(fov: Float, aspect: Float, near: Float, far: Float): Matrix4F {
        val perspective = Matrix4F()

        val f = 1f / tan(fov / 2f)

        perspective.m00 = f / aspect
        perspective.m11 = f
        perspective.m22 = (far + near) / (near - far)
        perspective.m32 = -1f
        perspective.m23 = 2f * far * near / (near - far)

        return perspective
    }

    fun translate(x: Float, y: Float, z: Float): Matrix4F {
        val translation = Matrix4F()

        translation.m03 = x
        translation.m13 = y
        translation.m23 = z

        return translation
    }

    fun rotate(angle: Float, xNum: Float, yNum: Float, zNum: Float): Matrix4F {

        val rotation = Matrix4F()
        val c = cos(Math.toRadians(angle.toDouble())).toFloat()
        val s = sin(Math.toRadians(angle.toDouble())).toFloat()

        var x = xNum
        var y = yNum
        var z = zNum

        var vec = Vector3F(x, y, z)
        if (vec.length() != 1f) {
            vec = vec.normalize()
            x = vec.x
            y = vec.y
            z = vec.z
        }

        rotation.m00 = x * x * (1f - c) + c
        rotation.m10 = y * x * (1f - c) + z * s
        rotation.m20 = x * z * (1f - c) - y * s
        rotation.m01 = x * y * (1f - c) - z * s
        rotation.m11 = y * y * (1f - c) + c
        rotation.m21 = y * z * (1f - c) + x * s
        rotation.m02 = x * z * (1f - c) + y * s
        rotation.m12 = y * z * (1f - c) - x * s
        rotation.m22 = z * z * (1f - c) + c

        return rotation
    }

    fun scale(x: Float, y: Float, z: Float): Matrix4F {
        val scale = Matrix4F()

        scale.m00 = x
        scale.m11 = y
        scale.m22 = z

        return scale
    }

}