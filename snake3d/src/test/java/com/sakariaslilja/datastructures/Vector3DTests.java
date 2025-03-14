package com.sakariaslilja.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.function.Predicate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Vector3DTests {

    @Test
    @DisplayName("Vector3D toString")
    public void returnsCorrectString() {
        Vector3D vector3d = new Vector3D(0, 0, 0);
        String expected = "Vector3D(0, 0, 0)";
        assertEquals(expected, vector3d.toString(), "Vector3D(0, 0, 0) should be returned when toString is called");
    }

    @Test
    @DisplayName("Vector3D getX")
    public void getX() {
        Vector3D vector3d = new Vector3D(0, 0, 0);
        int expected = 0;
        assertEquals(expected, vector3d.getX(), "getX() should return correct value");
    }

    @Test
    @DisplayName("Vector3D getY")
    public void getY() {
        Vector3D vector3d = new Vector3D(0, 5, 0);
        int expected = 5;
        assertEquals(expected, vector3d.getY(), "getY() should return correct value");
    }

    @Test
    @DisplayName("Vector3D getZ")
    public void getZ() {
        Vector3D vector3d = new Vector3D(0, 0, -3);
        int expected = -3;
        assertEquals(expected, vector3d.getZ(), "getZ() should return correct value");
    }

    @Test
    @DisplayName("Vector3D add")
    public void addVector3D() {
        Vector3D first = Heading.RIGHT.vec;
        Vector3D second = Heading.DOWN.vec;
        Vector3D result = first.add(second);
        Vector3D expected = new Vector3D(1, 1, 0);
        assertEquals(expected, result, "The add method should work as expected");
    }

    @Test
    @DisplayName("Vector3D negate")
    public void negateVector3D() {
        Vector3D vector3d = Heading.BACKWARD.vec;
        Vector3D expected = Heading.FORWARD.vec;
        assertEquals(expected, vector3d.neg(), "The neg method should work as expected");
    }

    @Test
    @DisplayName("Vector3D scalar multiply")
    public void scalarMul() {
        int scalar = 5;
        Vector3D vector3d = Heading.RIGHT.vec;
        Vector3D expected = new Vector3D(scalar, 0, 0);
        assertEquals(expected, vector3d.mul(scalar), "The mul method should work as expected");
    }

    @Test
    @DisplayName("Vector3D equals")
    public void vectorEquals() {
        Vector3D backward = Heading.BACKWARD.vec;
        Vector3D forward = Heading.FORWARD.vec;
        Vector3D custom = new Vector3D(0, 0, 1);
        assertEquals(forward, forward, "The equals method should return true if equal");
        assertNotEquals(backward, forward, "The equals method should return false when not equal");
        assertEquals(forward, custom, "The equals method should return true if the values are the same");
    }

    @Test
    @DisplayName("Vector3D forAll")
    public void vectorForAll() {
        Vector3D vector3d = new Vector3D(2, 0, 4);

        assertNotEquals(true, vector3d.forAll((c) -> c < 0), "The forAll method should return false");
        assertEquals(true, vector3d.forAll((c) -> c >= 0), "The forAll method should return true");
    }

    @Test
    @DisplayName("Vector3D conversion to DoubleVector3D")
    public void vectorConvert() {
        Vector3D vector3d = new Vector3D(1, 2, 3);
        DoubleVector3D converted = vector3d.toDoubleVector3D();
        DoubleVector3D expected = new DoubleVector3D(1, 2, 3);

        assertEquals(expected, converted, "The toDoubleVector3D should work as expected");
    }

    @Test
    @DisplayName("Vector3D exists")
    public void vectorExists() {
        Vector3D vector3d = new Vector3D(1, 1, -1);
        Predicate<Integer> isNegative = (i) -> i < 0;
        Predicate<Integer> isZero = (i) -> i.intValue() == 0;

        assertEquals(true, vector3d.exists(isNegative), "The exists method should work");
        assertNotEquals(true, vector3d.exists(isZero), "The exists method should not return true for false values");
    }

    @Test
    @DisplayName("Vector3D cross-product")
    public void crossProduct() {
        assertEquals(Heading.RIGHT.vec, Heading.DOWN.vec.crossProd(Heading.FORWARD.vec), "The cross product should work as expected");
    }

    @Test
    @DisplayName("Vector3D rotate")
    public void rotateVector3D() {
        assertEquals(Heading.DOWN.vec, Heading.FORWARD.vec.rotateX(false), "RotateX should work properly");
        assertEquals(Heading.RIGHT.vec, Heading.FORWARD.vec.rotateY(true), "RotateY should work properly");
        assertEquals(Heading.DOWN.vec, Heading.RIGHT.vec.rotateZ(true), "RotateZ should work properly");
    }
    
}
