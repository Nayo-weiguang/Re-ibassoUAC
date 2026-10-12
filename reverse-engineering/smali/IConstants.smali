### public static idToString(B)Ljava/lang/String;
    const/16 v0, 32
    if-eq v2, v0, +0d1h
    const/16 v0, 33
    if-eq v2, v0, +0cah
    const/16 v0, 65
    if-eq v2, v0, +0c3h
    const/16 v0, 66
    if-eq v2, v0, +0bch
    const/16 v0, 96
    if-eq v2, v0, +0b5h
    const/16 v0, 97
    if-eq v2, v0, +0aeh
    const/16 v0, 99
    if-eq v2, v0, +0a7h
    packed-switch v2, +00000bah
    packed-switch v2, +00000efh
    new-instance v0, Ljava/lang/StringBuilder;
    invoke-direct v0, Ljava/lang/StringBuilder;-><init>()V
    const-string v1, "Unknown ID "
    invoke-virtual v0, v1, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    invoke-virtual v0, v2, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;
    invoke-virtual v0, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    move-result-object v2
    return-object v2
    const-string v2, "ID_GET_FILTER"
    return-object v2
    const-string v2, "ID_GET_FPGA_VERSION"
    return-object v2
    const-string v2, "ID_GET_OUTPUT_2"
    return-object v2
    const-string v2, "ID_GET_OUTPUT_1"
    return-object v2
    const-string v2, "ID_GET_GAIN_2"
    return-object v2
    const-string v2, "ID_GET_GAIN_1"
    return-object v2
    const-string v2, "ID_NATIVE_REPORT_VOLUME"
    return-object v2
    const-string v2, "ID_SET_REGISTER_TEST"
    return-object v2
    const-string v2, "ID_GET_REGISTER_TEST"
    return-object v2
    const-string v2, "ID_GET_DSD_VOLUME_RIGHT_2"
    return-object v2
    const-string v2, "ID_GET_DSD_VOLUME_RIGHT_1"
    return-object v2
    const-string v2, "ID_GET_DSD_VOLUME_LEFT_2"
    return-object v2
    const-string v2, "ID_GET_DSD_VOLUME_LEFT_1"
    return-object v2
    const-string v2, "ID_GET_DC03PRO_DIGITAL_FILTER_2"
    return-object v2
    const-string v2, "ID_GET_DC03PRO_DIGITAL_FILTER_1"
    return-object v2
    const-string v2, "ID_GET_DIGITAL_FILTER_INTERNAL_2"
    return-object v2
    const-string v2, "ID_GET_DIGITAL_FILTER_INTERNAL_1"
    return-object v2
    const-string v2, "ID_GET_DIGITAL_FILTER_2"
    return-object v2
    const-string v2, "ID_GET_DIGITAL_FILTER_1"
    return-object v2
    const-string v2, "ID_GET_VOLUME_RIGHT_2"
    return-object v2
    const-string v2, "ID_GET_VOLUME_RIGHT_1"
    return-object v2
    const-string v2, "ID_SET_CODEC_EXTERNAL"
    return-object v2
    const-string v2, "ID_SET_OUTPUT_2"
    return-object v2
    const-string v2, "ID_SET_OUTPUT_1"
    return-object v2
    const-string v2, "ID_SET_GAIN_2"
    return-object v2
    const-string v2, "ID_SET_GAIN_1"
    return-object v2
    const-string v2, "ID_SET_VOLUME_RIGHT_ROOM"
    return-object v2
    const-string v2, "ID_SET_VOLUME_LEFT_ROOM"
    return-object v2
    const-string v2, "ID_SET_DIGITAL_FILTER_2"
    return-object v2
    const-string v2, "ID_SET_DIGITAL_FILTER_1"
    return-object v2
    const-string v2, "ID_SET_DSD_BALANCE_RIGHT_2"
    return-object v2
    const-string v2, "ID_SET_DSD_BALANCE_RIGHT_1"
    return-object v2
    const-string v2, "ID_SET_DSD_BALANCE_LEFT_2"
    return-object v2
    const-string v2, "ID_SET_DSD_BALANCE_LEFT_1"
    return-object v2
    const-string v2, "ID_SET_DSD_VOLUME_RIGHT_2"
    return-object v2
    const-string v2, "ID_SET_DSD_VOLUME_RIGHT_1"
    return-object v2
    const-string v2, "ID_SET_DSD_VOLUME_LEFT_2"
    return-object v2
    const-string v2, "ID_SET_DSD_VOLUME_LEFT_1"
    return-object v2
    const-string v2, "ID_SET_BALANCE_RIGHT_2"
    return-object v2
    const-string v2, "ID_SET_BALANCE_RIGHT_1"
    return-object v2
    const-string v2, "ID_SET_BALANCE_LEFT_2"
    return-object v2
    const-string v2, "ID_SET_BALANCE_LEFT_1"
    return-object v2
    const-string v2, "ID_SET_VOLUME_RIGHT_2"
    return-object v2
    const-string v2, "ID_SET_VOLUME_RIGHT_1"
    return-object v2
    const-string v2, "ID_SET_VOLUME_LEFT_2"
    return-object v2
    const-string v2, "ID_SET_VOLUME_LEFT_1"
    return-object v2
    const-string v2, "ID_PCM_NATIVE_REPORT"
    return-object v2
    const-string v2, "ID_GET_UAC"
    return-object v2
    const-string v2, "ID_GET_VOLUME_KEY_CONTROL"
    return-object v2
    const-string v2, "ID_GET_SPDIF"
    return-object v2
    const-string v2, "ID_GET_VOLUME_LEFT_2"
    return-object v2
    const-string v2, "ID_GET_VOLUME_LEFT_1"
    return-object v2
    const-string v2, "ID_SET_UAC"
    return-object v2
    const-string v2, "ID_SET_CODEC_EXTERNAL_ROOM"
    return-object v2
    packed-switch-payload 0 1 2 3 4 5 6 7 8 9 a b c d e f 10 11 12 13 14 15 16 17 18 19
    packed-switch-payload 45 46 47 48 49 4a 4b 4c 4d 4e 4f 50 51 52 53 54 55 56 57 58 59