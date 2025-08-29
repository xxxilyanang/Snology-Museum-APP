package com.example.gxcg.util;

/**
 * Created by Administrator on 2017/3/12.
 */


import java.util.ArrayList;
import java.util.List;

public class StrListChange {
    // 将字符串转换到List,     结果:a<#>b<#>c|a<#>b<#>c -> {[a,b,c],[a,b,c]}
    public static List<String[]> StrToList(String info) {
        List<String[]> list = new ArrayList<String[]>();
        if (info.length() == 0) {
            return list;
        }
        if(info.length()>0) {
            if (info.substring(info.length() - 1).equals("\n") || info.substring(info.length() - 1).equals("\r")) {
                info = info.replaceAll("\r|\n", "");
            }
        }
        String[] s = info.split("\\|");
        int num = 0;
        for (String ss : s) {
            num = 0;
            String[] temp = ss.split("<#>");
            String[] midd = new String[temp.length];
            for (String a : temp) {
                midd[num++] = a;
            }
            list.add(midd);
        }
        return list;
    }

    // 从字符串转换成数组
    public static String[] StrToArray(String info) {
        int num = 0;
        if(info.length()>0) {
            if (info.substring(info.length() - 1).equals("\n") || info.substring(info.length() - 1).equals("\r")) {
                info = info.replaceAll("\r|\n", "");
            }
        }
        String[] first = info.split("\\|");
        for (int i = 0; i < first.length; i++) {
            String[] temp1 = first[i].split("<#>");
            num += temp1.length;
        }
        String[] temp2 = new String[num];
        num = 0;
        for (String second : first) {
            String[] temp3 = second.split("<#>");
            for (String third : temp3) {
                temp2[num] = third;
                num++;
            }
        }
        return temp2;
    }

    // 将List转换成字符串
    public static String ListToStr(List<String[]> list) {
        String mess = "";
        List<String[]> ls = new ArrayList<String[]>();
        ls = list;
        for (int i = 0; i < ls.size(); i++) {
            String[] ss = ls.get(i);
            for (String s : ss) {
                mess += s + "<#>";
            }
            mess += "|";
        }
        return mess;
    }

    // 排除相同的内容 返回字符串
    public static String Streamline(String mess) {
        String[] str = mess.split("<#>");
        String info = "";
        for (int i = 0; i < str.length - 1; i++) {
            String temp = str[i];
            String temp2 = str[i + 1];
            if (!temp.equals(temp2)) {
                info += temp + "<#>";
            } else {
                continue;
            }
        }
        return info + str[str.length - 1];
    }

    //List变成二维数组，表格用
    public static String[][] ListToTwoStringArray(List<String[]> ls)
    {
        String str[][]=new String[ls.size()][ls.get(0).length];
        for(int i=0;i<ls.size();i++)
        {
            for(int j=0;j<ls.get(i).length;j++)
            {
                str[i][j]=ls.get(i)[j];
            }
        }
        return str;
    }

    //将List编程二维String
    public static String ListToTwoStr(List<String[]> ls)
    {
        String result="";
        for(int i=0;i<ls.size();i++)
        {
            for(int j=0;j<ls.get(i).length;j++)
            {
                if(j==ls.get(i).length-1)
                {
                    //最后一段加!
                    result+=ls.get(i)[j]+"<!>";
                    continue;
                }
                result+=ls.get(i)[j]+"<#>";
            }
        }
        result=result.substring(0,result.length()-3);
        return result;
    }

    //将二维String变成二维数组
    public static String[][] StrToTwoArray(String ms)
    {

        String mest[]=ms.split("<!>");
        String t[]=mest[0].split("<#>");
        //创建数组
        String result[][]=new String[mest.length][t.length];
        for(int i=0;i<mest.length;i++)
        {
            String mee[]=mest[i].split("<#>");
            for(int j=0;j<mee.length;j++)
            {
                result[i][j]=mee[j];
            }
        }
        return result;
    }
}
