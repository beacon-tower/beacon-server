package com.beacon.asch.sdk;

/**
 * Created by eagle on 17-7-16.
 */
public class TestData {

    //luckyhua
    public static final String root = "http://sofly.cc:4096";
    public static final String secret = "season adult nerve umbrella wheel rate beach copper element sponsor force enhance";
    public static final String address = "ABiJCTd8dx7vjs2TAnGYD5foRU9PsjDf9e";

    public static final String secondSecret = "zhough212";
    public static final String userName = "asch_g001";

    //asch_g101 11705168753296944226 00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9
    public static final String targetAddress = "A8v3qQm3C6yRjvdjgU1xvhxAfFcGBU3S6n";

    //asch_g101	11705168753296944226 00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9
    //asch_g96	16093201530526106149 0225c340addf24211f4b51ea24cb0565c67f09600f57bad89af350b38b08a366
    //asch_g32	4354832300657989346 0ae2e3bcd8c959bccc34445a9473eab1bece60300f3aa00d89612923470dee75
    public static final String[] voted = "00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9,0225c340addf24211f4b51ea24cb0565c67f09600f57bad89af350b38b08a366,0ae2e3bcd8c959bccc34445a9473eab1bece60300f3aa00d89612923470dee75".split(",");

    //asch_g23	4752137788839516181 486179424b4bcfaf7960a4a121277d73e4a7c9e0c27b254edf978762d5a6dfe6
    //asch_g40	16494449392359591122 49b369ff2635e2ac083d62a6c59baf872fbdef6990297f296c95e1830118aba1
    public static final String[] canceled = "486179424b4bcfaf7960a4a121277d73e4a7c9e0c27b254edf978762d5a6dfe6,49b369ff2635e2ac083d62a6c59baf872fbdef6990297f296c95e1830118aba1".split(",");


    public static final String blockId = "a9a4d8263b68a238bd934943624a2a59f13525b32b0957ea2328d4ff346de174";

    public static final long blockHeight = 6900;

    public static final String blockHash = "979fa5571901256bb10e42d5ae88e5f5108b6f684938beaf4281bf9cfa2041850409eee45cc5f2a37aa93717f6079da9a549496c53ee9ff510c97c2e0c0b6c0a";

    public static final String transactionId = "12936058387774240556";

    public static final String senderPublicKey = "9d986947594da0223958a88df625f0e8fb1f4bcc278845b3fe22b7f5aeef50ab";

    public static final String dappId = "865179a276dfba7f552d76824c732bd5d506e890c9c3d8fc7e0665d5b1db9263";

    public static final String dappAddress = "ACvpFUYnFLSSBgoHtjRbvFck7ABBPm6udy";

    public static String publicKey(){
        return AschSDK.Helper.getPublicKey(secret);
    }

}
