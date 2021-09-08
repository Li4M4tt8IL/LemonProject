package me.pm.lemon.utils;

import me.pm.lemon.utils.generalUtils.Vec4;

public interface IMatrix4f {
    void multiplyMatrix(Vec4 v, Vec4 out);
}