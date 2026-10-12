package com.ibasso.volume.eq;

import com.ibasso.volume.IConstants;
import kotlin.jvm.internal.ByteCompanionObject;
public class WriteReg {
    private void WriteRegs() {
        int[] iArr = {2, 2, 2, 2, 2, 2, 2, 2};
        int[] iArr2 = new int[48];
        byte[] bArr = {1, 2, 4, 8, IConstants.ID_SET_DSD_BALANCE_RIGHT_2, IConstants.ID_SET_CODEC_EXTERNAL_ROOM, 64, ByteCompanionObject.MIN_VALUE};
        for (int i = 0; i < 8; i++) {
            int i2 = iArr[i];
        }
        int[] iArr3 = new int[40];
        for (int i3 = 0; i3 < 8; i3++) {
            if (iArr[i3] != 0) {
                byte b2 = bArr[i3];
            }
        }
        for (int i4 = 0; i4 < 8; i4++) {
            int i5 = i4 * 5;
            int i6 = i4 * 6;
            iArr3[i5] = iArr2[i6];
            iArr3[i5 + 1] = iArr2[i6 + 1];
            iArr3[i5 + 2] = iArr2[i6 + 2];
            iArr3[i5 + 3] = iArr2[i6 + 4];
            iArr3[i5 + 4] = iArr2[i6 + 5];
        }
        WriteRegs(iArr3);
    }

    private void WriteRegs(int[] iArr) {
        int[] iArr2 = {1, 1, 1, 1, 1, 1, 1, 1};
        for (int i = 0; i < 8; i++) {
            int i2 = iArr2[i];
        }
    }
}
