package com.collection;

import org.junit.Test;

import java.util.*;

/**
 * @author: tangJ
 * @Date: 2018/11/23 10:20
 * @description:
 */
public class CollectionsDemo {

    Random random = new Random(100);

    public List<Integer> getList(int size) {
        List<Integer> list = new ArrayList<>();
        int nextSize = size * 2;
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt(nextSize));
        }
        System.out.println("before------------");
        System.out.println(list);
        return list;
    }

    /**
     * 集合的排序
     */
    @Test
    public void testSort() {
        List<Integer> list = getList(1000);
        System.out.println("after-------------");
        Collections.sort(list);
        System.out.println(list);
    }

    /**
     * 二分法查找
     */
    @Test
    public void testSearch() {
        List<Integer> list = getList(1000);
        list.add(59);
        int location = Collections.binarySearch(list, 59);
        System.out.println("the location is:  " + location);
    }

    /**
     * 打乱顺序
     */
    @Test
    public void testShuffle() {
        List<Integer> list = getList(1000);
        System.out.println("after-------------");
        Collections.shuffle(list);
        System.out.println(list);
    }

    /**
     * 获取最小值
     */
    @Test
    public void testMin() {
        List<Integer> list = getList(1000);
        Integer min = Collections.min(list);
        System.out.println("the min value: " + min);

    }

    /**
     * 获取最大值
     */
    @Test
    public void testMax() {
        List<Integer> list = getList(1000);
        Integer min = Collections.max(list);
        System.out.println("the min value: " + min);
    }

    /**
     * 旋转集合
     */
    @Test
    public void testRotate() {
        List<Integer> list = getList(50);
        int distance = 2;
        Collections.rotate(list, distance);
        System.out.println(list);
    }

    /**
     * 替换元素
     * fill 替换全部元素
     * replaceAll 替换指定的元素(全部)
     */
    @Test
    public void testReplaceAll() {
        List<Integer> list = getList(50);
        Collections.fill(list, 100);
        System.out.println(list);
        Collections.replaceAll(list, 100, 50);
        System.out.println(list);
    }

    /**
     * 创建一个无法修改修改的集合对象
     */
    @Test
    public void testUnModifyCollections() {
        List<Integer> list = getList(50);
        list = Collections.unmodifiableList(list);
        list.add(2);
    }

    /**
     * 创建一个同步集合
     * synchronizedList 方法，重新创建一个集合对象，这个集合对象中，所有方法都是加上了锁
     */
    @Test
    public void testSyncCollection(){
        List<Integer> list = getList(50);
        list = Collections.synchronizedList(list);
        list.add(54);
    }


    /**
     * 集合在添加元素时，指定元素检查
     */
    @Test
    public void testCheckCollection(){
        List<Integer> list = getList(60);
        list = Collections.checkedList(list,Integer.class);
        list.add(51);
    }


}
