package com.twelvemonkeys.imageio.plugins.psd;

import java.awt.*;
import java.awt.color.ColorSpace;

public class RgbMultichannelColorSpace extends ColorSpace {

    private static final long serialVersionUID = -1821569090707520704L;

    private static final RgbMultichannelColorSpace[] instances = new RgbMultichannelColorSpace[20];

    public static final RgbMultichannelColorSpace getInstance(int numComponents) {
        if (instances[numComponents] == null) {
            instances[numComponents] = new RgbMultichannelColorSpace(numComponents);
        }
        return instances[numComponents];
    }

    private ColorSpace srgb = ColorSpace.getInstance(ColorSpace.CS_sRGB);

    private RgbMultichannelColorSpace(int numComponents) {
        super(ColorSpace.TYPE_FCLR, numComponents);
    }

    private void checkNumComponents(float[] colorvalue) {
        checkNumComponents(colorvalue, getNumComponents());
    }

    private void checkNumComponents(float[] colorvalue, int expected) {
        if (colorvalue == null) {
            throw new NullPointerException("color value may not be null");
        }
        if (colorvalue.length != expected) {
            throw new IllegalArgumentException("Expected " + expected
                    + " components, but got " + colorvalue.length);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getMinValue(int component) {
        return 0f;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getMaxValue(int component) {
        return 255f;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName(int component) {
        return "bogus";
    }

    //Note: the conversion functions used here were mostly borrowed from Apache Commons Sanselan
    //and adjusted to the local requirements.

    /**
     * {@inheritDoc}
     */
    @Override
    public float[] fromCIEXYZ(float[] colorvalue) {
        float[] rgb = srgb.fromCIEXYZ(colorvalue);
        return fromRGB(rgb);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float[] fromRGB(float[] rgbvalue) {
        float[] newArray = new float[getNumComponents()];
        System.arraycopy(rgbvalue, 0, newArray, 0, rgbvalue.length);
        return newArray;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float[] toCIEXYZ(float[] colorvalue) {
        return srgb.toCIEXYZ(colorvalue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float[] toRGB(float[] colorvalue) {
        return colorvalue;
    }
}
