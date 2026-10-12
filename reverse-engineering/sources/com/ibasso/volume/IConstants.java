package com.ibasso.volume;
public interface IConstants {
    public static final int ID_DC03PRO = 1;
    public static final int ID_DC06PRO = 2;
    public static final byte ID_GET_DC03PRO_DIGITAL_FILTER_1 = 75;
    public static final byte ID_GET_DC03PRO_DIGITAL_FILTER_2 = 76;
    public static final byte ID_GET_DIGITAL_FILTER_1 = 71;
    public static final byte ID_GET_DIGITAL_FILTER_2 = 72;
    public static final byte ID_GET_DIGITAL_FILTER_INTERNAL_1 = 73;
    public static final byte ID_GET_DIGITAL_FILTER_INTERNAL_2 = 74;
    public static final byte ID_GET_DSD_VOLUME_LEFT_1 = 77;
    public static final byte ID_GET_DSD_VOLUME_LEFT_2 = 78;
    public static final byte ID_GET_DSD_VOLUME_RIGHT_1 = 79;
    public static final byte ID_GET_DSD_VOLUME_RIGHT_2 = 80;
    public static final byte ID_GET_FILTER = 89;
    public static final byte ID_GET_FPGA_VERSION = 88;
    public static final byte ID_GET_GAIN_1 = 84;
    public static final byte ID_GET_GAIN_2 = 85;
    public static final byte ID_GET_INIT = 98;
    public static final byte ID_GET_OUTPUT_1 = 86;
    public static final byte ID_GET_OUTPUT_2 = 87;
    public static final byte ID_GET_REGISTER_TEST = 81;
    public static final byte ID_GET_SPDIF = 96;
    public static final byte ID_GET_UAC = 99;
    public static final byte ID_GET_VOLUME_KEY_CONTROL = 97;
    public static final byte ID_GET_VOLUME_LEFT_1 = 65;
    public static final byte ID_GET_VOLUME_LEFT_2 = 66;
    public static final byte ID_GET_VOLUME_RIGHT_1 = 69;
    public static final byte ID_GET_VOLUME_RIGHT_2 = 70;
    public static final int ID_IBASSO_NATIVE_REPORT_FILTER = 34;
    public static final int ID_IBASSO_NATIVE_REPORT_KEY_CONTROL = 66;
    public static final int ID_IBASSO_NATIVE_REPORT_SPDIF = 50;
    public static final int ID_IBASSO_NATIVE_REPORT_VOLUME = 18;
    public static final byte ID_NATIVE_REPORT_VOLUME = 83;
    public static final int ID_PCM_NATIVE_REPORT = 0;
    public static final byte ID_SET_BALANCE_LEFT_1 = 5;
    public static final byte ID_SET_BALANCE_LEFT_2 = 6;
    public static final byte ID_SET_BALANCE_RIGHT_1 = 7;
    public static final byte ID_SET_BALANCE_RIGHT_2 = 8;
    public static final byte ID_SET_CODEC_EXTERNAL = 25;
    public static final byte ID_SET_CODEC_EXTERNAL_ROOM = 32;
    public static final byte ID_SET_DIGITAL_FILTER_1 = 17;
    public static final byte ID_SET_DIGITAL_FILTER_2 = 18;
    public static final byte ID_SET_DSD_BALANCE_LEFT_1 = 13;
    public static final byte ID_SET_DSD_BALANCE_LEFT_2 = 14;
    public static final byte ID_SET_DSD_BALANCE_RIGHT_1 = 15;
    public static final byte ID_SET_DSD_BALANCE_RIGHT_2 = 16;
    public static final byte ID_SET_DSD_VOLUME_LEFT_1 = 9;
    public static final byte ID_SET_DSD_VOLUME_LEFT_2 = 10;
    public static final byte ID_SET_DSD_VOLUME_RIGHT_1 = 11;
    public static final byte ID_SET_DSD_VOLUME_RIGHT_2 = 12;
    public static final byte ID_SET_GAIN_1 = 21;
    public static final byte ID_SET_GAIN_2 = 22;
    public static final byte ID_SET_OUTPUT_1 = 23;
    public static final byte ID_SET_OUTPUT_2 = 24;
    public static final byte ID_SET_REGISTER_TEST = 82;
    public static final byte ID_SET_UAC = 33;
    public static final byte ID_SET_VOLUME_LEFT_1 = 1;
    public static final byte ID_SET_VOLUME_LEFT_2 = 2;
    public static final byte ID_SET_VOLUME_LEFT_ROOM = 19;
    public static final byte ID_SET_VOLUME_RIGHT_1 = 3;
    public static final byte ID_SET_VOLUME_RIGHT_2 = 4;
    public static final byte ID_SET_VOLUME_RIGHT_ROOM = 20;
    public static final int ID_TEST = 3;

    static String idToString(byte b2) {
        if (b2 != 32) {
            if (b2 != 33) {
                if (b2 != 65) {
                    if (b2 != 66) {
                        if (b2 != 96) {
                            if (b2 != 97) {
                                if (b2 != 99) {
                                    switch (b2) {
                                        case 0:
                                            return "ID_PCM_NATIVE_REPORT";
                                        case 1:
                                            return "ID_SET_VOLUME_LEFT_1";
                                        case 2:
                                            return "ID_SET_VOLUME_LEFT_2";
                                        case 3:
                                            return "ID_SET_VOLUME_RIGHT_1";
                                        case 4:
                                            return "ID_SET_VOLUME_RIGHT_2";
                                        case 5:
                                            return "ID_SET_BALANCE_LEFT_1";
                                        case 6:
                                            return "ID_SET_BALANCE_LEFT_2";
                                        case 7:
                                            return "ID_SET_BALANCE_RIGHT_1";
                                        case 8:
                                            return "ID_SET_BALANCE_RIGHT_2";
                                        case 9:
                                            return "ID_SET_DSD_VOLUME_LEFT_1";
                                        case 10:
                                            return "ID_SET_DSD_VOLUME_LEFT_2";
                                        case 11:
                                            return "ID_SET_DSD_VOLUME_RIGHT_1";
                                        case 12:
                                            return "ID_SET_DSD_VOLUME_RIGHT_2";
                                        case 13:
                                            return "ID_SET_DSD_BALANCE_LEFT_1";
                                        case 14:
                                            return "ID_SET_DSD_BALANCE_LEFT_2";
                                        case 15:
                                            return "ID_SET_DSD_BALANCE_RIGHT_1";
                                        case 16:
                                            return "ID_SET_DSD_BALANCE_RIGHT_2";
                                        case 17:
                                            return "ID_SET_DIGITAL_FILTER_1";
                                        case 18:
                                            return "ID_SET_DIGITAL_FILTER_2";
                                        case 19:
                                            return "ID_SET_VOLUME_LEFT_ROOM";
                                        case 20:
                                            return "ID_SET_VOLUME_RIGHT_ROOM";
                                        case 21:
                                            return "ID_SET_GAIN_1";
                                        case 22:
                                            return "ID_SET_GAIN_2";
                                        case 23:
                                            return "ID_SET_OUTPUT_1";
                                        case 24:
                                            return "ID_SET_OUTPUT_2";
                                        case 25:
                                            return "ID_SET_CODEC_EXTERNAL";
                                        default:
                                            switch (b2) {
                                                case 69:
                                                    return "ID_GET_VOLUME_RIGHT_1";
                                                case 70:
                                                    return "ID_GET_VOLUME_RIGHT_2";
                                                case 71:
                                                    return "ID_GET_DIGITAL_FILTER_1";
                                                case 72:
                                                    return "ID_GET_DIGITAL_FILTER_2";
                                                case 73:
                                                    return "ID_GET_DIGITAL_FILTER_INTERNAL_1";
                                                case 74:
                                                    return "ID_GET_DIGITAL_FILTER_INTERNAL_2";
                                                case 75:
                                                    return "ID_GET_DC03PRO_DIGITAL_FILTER_1";
                                                case 76:
                                                    return "ID_GET_DC03PRO_DIGITAL_FILTER_2";
                                                case 77:
                                                    return "ID_GET_DSD_VOLUME_LEFT_1";
                                                case 78:
                                                    return "ID_GET_DSD_VOLUME_LEFT_2";
                                                case 79:
                                                    return "ID_GET_DSD_VOLUME_RIGHT_1";
                                                case 80:
                                                    return "ID_GET_DSD_VOLUME_RIGHT_2";
                                                case 81:
                                                    return "ID_GET_REGISTER_TEST";
                                                case 82:
                                                    return "ID_SET_REGISTER_TEST";
                                                case 83:
                                                    return "ID_NATIVE_REPORT_VOLUME";
                                                case 84:
                                                    return "ID_GET_GAIN_1";
                                                case 85:
                                                    return "ID_GET_GAIN_2";
                                                case 86:
                                                    return "ID_GET_OUTPUT_1";
                                                case 87:
                                                    return "ID_GET_OUTPUT_2";
                                                case 88:
                                                    return "ID_GET_FPGA_VERSION";
                                                case 89:
                                                    return "ID_GET_FILTER";
                                                default:
                                                    return "Unknown ID " + ((int) b2);
                                            }
                                    }
                                }
                                return "ID_GET_UAC";
                            }
                            return "ID_GET_VOLUME_KEY_CONTROL";
                        }
                        return "ID_GET_SPDIF";
                    }
                    return "ID_GET_VOLUME_LEFT_2";
                }
                return "ID_GET_VOLUME_LEFT_1";
            }
            return "ID_SET_UAC";
        }
        return "ID_SET_CODEC_EXTERNAL_ROOM";
    }
}
