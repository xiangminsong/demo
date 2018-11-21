package com.sn;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class GroupUtil {
	
	/**
     * 集合分组调用算法
     *
     * @param args
     * @param groupCapactiy 每组元素数量
     * @return
     */
    public static <T> List<List<T>> groupList(List<T> args, int groupCapactiy) {
        List<List<T>> result = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(args) || groupCapactiy < 0) {
            return result;
        }
        int total = args.size();
        int group = 0;
        for (int i = 0; i < total; i = i + groupCapactiy) {
            group++;
        }
        for (int i = 0; i < group; i++) {
            int start = i * groupCapactiy;
            int end;
            if (i == group - 1) {
                end = total;
            } else {
                end = start + groupCapactiy;
            }
            List<T> objList = args.subList(start, end);
            result.add(objList);
        }
        return result;
    }

    /**
     * 给定组数n，将集合分为n个组
     *
     * @param args
     * @param groupCount 组的数量
     * @return
     */
    public static <T> List<List<T>> groupByCount(List<T> args, int groupCount) {
        List<List<T>> result = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(args)) {
            return result;
        }
        int total = args.size();
        int k = total % groupCount;
        int n = total % groupCount;
        int size = total / groupCount;

        int realGrouNum = groupCount;
        if (size == 0) {
            realGrouNum = k;
        }
        for (int i = 0; i < realGrouNum; i++) {
            int start = i * size;
            int end = start + size;
            if (k > 0) {
                start += i;
                end += i;
                end++;
            } else {
                start += n;
                end += n;
            }
            List<T> objList = args.subList(start, end);
            if (!CollectionUtils.isEmpty(objList)) {
                result.add(objList);
            }
            k--;
        }
        return result;
    }


    /**
     * 给定分组个数n，及每组希望个数m，则当总数大于m*n时，分为n组，若总数<m*n，则按每组m个进行分组
     *
     * @param args
     * @param groupCount    组的数量
     * @param groupCapactiy 每组期望的元素数量
     * @return
     */
    public static <T> List<List<T>> groupByCount(List<T> args, int groupCount, int groupCapactiy) {
        List<List<T>> result = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(args)) {
            return result;
        }
        int total = args.size();
        if (total >= groupCapactiy * (groupCount) || groupCapactiy < 1) {
            return groupByCount(args, groupCount);
        }

        int realGrouNum = total / groupCapactiy + 1;
        for (int i = 0; i < realGrouNum; i++) {
            int start = i * groupCapactiy;
            int end = start + groupCapactiy;
            if (end > total) {
                end = total;
            }
            List<T> objList = args.subList(start, end);
            if (!CollectionUtils.isEmpty(objList)) {
                result.add(objList);
            }
        }

        return result;
    }

   /* public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 2; i++) {
            list.add(i);
        }
        System.out.println(groupByCount(list, 8, 0).size());
        System.out.println(groupByCount(list, 8, 0));
    }*/

}
