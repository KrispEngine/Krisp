package engine.krisp.position

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Represents an immutable point in 2D space
 */
class Point(
    val x: Float,
    val y: Float
) {

    companion object {
        val ZERO = Point(0.0f, 0.0f)
    }

    constructor() : this(0.0f, 0.0f)
    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())
    constructor(x: Double, y: Double) : this(x.toFloat(), y.toFloat())

    fun add(point: Point): Point {
        return Point(x + point.x, y + point.y)
    }

    fun add(x: Float, y: Float): Point {
        return Point(this.x + x, this.y + y)
    }

    fun add(x: Int, y: Int): Point {
        return Point(this.x + x, this.y + y)
    }

    fun add(x: Double, y: Double): Point {
        return Point(this.x + x, this.y + y)
    }

    fun subtract(point: Point): Point {
        return Point(x - point.x, y - point.y)
    }

    fun subtract(x: Float, y: Float): Point {
        return Point(this.x - x, this.y - y)
    }

    fun subtract(x: Int, y: Int): Point {
        return Point(this.x - x, this.y - y)
    }

    fun subtract(x: Double, y: Double): Point {
        return Point(this.x - x, this.y - y)
    }

    fun multiply(point: Point): Point {
        return Point(x * point.x, y * point.y)
    }

    fun multiply(x: Float, y: Float): Point {
        return Point(this.x * x, this.y * y)
    }

    fun multiply(x: Int, y: Int): Point {
        return Point(this.x * x, this.y * y)
    }

    fun multiply(x: Double, y: Double): Point {
        return Point(this.x * x, this.y * y)
    }

    fun divide(point: Point): Point {
        return Point(x / point.x, y / point.y)
    }

    fun divide(x: Float, y: Float): Point {
        return Point(this.x / x, this.y / y)
    }

    fun divide(x: Int, y: Int): Point {
        return Point(this.x / x, this.y / y)
    }

    fun divide(x: Double, y: Double): Point {
        return Point(this.x / x, this.y / y)
    }

    fun distance(point: Point): Float {
        return sqrt((x - point.x).toDouble().pow(2.0) + (y - point.y).toDouble().pow(2.0)).toFloat()
    }

    fun distance(x: Float, y: Float): Float {
        return sqrt((this.x - x).toDouble().pow(2.0) + (this.y - y).toDouble().pow(2.0)).toFloat()
    }

    fun distance(x: Int, y: Int): Float {
        return sqrt((this.x - x).toDouble().pow(2.0) + (this.y - y).toDouble().pow(2.0)).toFloat()
    }

    fun distance(x: Double, y: Double): Float {
        return sqrt((this.x - x).toDouble().pow(2.0) + (this.y - y).toDouble().pow(2.0)).toFloat()
    }

    fun distanceSquared(point: Point): Float {
        return (x - point.x).toDouble().pow(2.0).toFloat() + (y - point.y).toDouble().pow(2.0).toFloat()
    }

    fun distanceSquared(x: Float, y: Float): Float {
        return (this.x - x).toDouble().pow(2.0).toFloat() + (this.y - y).toDouble().pow(2.0).toFloat()
    }

    fun distanceSquared(x: Int, y: Int): Float {
        return (this.x - x).toDouble().pow(2.0).toFloat() + (this.y - y).toDouble().pow(2.0).toFloat()
    }

    fun distanceSquared(x: Double, y: Double): Float {
        return (this.x - x).toDouble().pow(2.0).toFloat() + (this.y - y).toDouble().pow(2.0).toFloat()
    }

    fun magnitude(): Float {
        return sqrt(x.toDouble().pow(2.0) + y.toDouble().pow(2.0)).toFloat()
    }

    fun magnitudeSquared(): Float {
        return x.toDouble().pow(2.0).toFloat() + y.toDouble().pow(2.0).toFloat()
    }

    fun normalize(): Point {
        val magnitude = magnitude()
        return Point(x / magnitude, y / magnitude)
    }

    fun dot(point: Point): Float {
        return x * point.x + y * point.y
    }

    fun dot(x: Float, y: Float): Float {
        return this.x * x + this.y * y
    }

    fun dot(x: Int, y: Int): Float {
        return this.x * x + this.y * y
    }

    fun dot(x: Double, y: Double): Float {
        return (this.x * x + this.y * y).toFloat()
    }

    fun cross(point: Point): Float {
        return x * point.y - y * point.x
    }

    fun cross(x: Float, y: Float): Float {
        return this.x * y - this.y * x
    }

    fun cross(x: Int, y: Int): Float {
        return this.x * y - this.y * x
    }


}