## 位运算
```

<<      :     左移运算符，num << 1,相当于num乘以2

>>      :     右移运算符，num >> 1,相当于num除以2

>>>    :     无符号右移，忽略符号位，空位都以0补齐

```






## String 源码阅读


###  boolean equals(Object anObject)
- 判断对象的值是否相等
- Object anObject 待比较的对象
```
    public boolean equals(Object anObject) {
        // 首先判断是否为，同一个对象，如果是，则true
        if (this == anObject) {
            return true;
        }
        // 不是同一个对象， 则判断对象类型是否为String
        if (anObject instanceof String) {
            String anotherString = (String)anObject;
            int n = value.length;
            // 判断 两个String中 字符数组长度是否相等
            if (n == anotherString.value.length) {
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                // 判断字符数组中的元素是否都相等，如果都相等，则true
                while (n-- != 0) {
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        // 对象类型不是String，返回false
        return false;
    }
    
```


### boolean startsWith(String prefix, int toffset)
- 判断一个字符串是否以特定的开头
- String prefix 特定开头的字符串
- int toffset 待比较字符串的偏移值

```
    public boolean startsWith(String prefix, int toffset) {
        // 待比较的字符串的字符数组
        char ta[] = value;
        // 比较偏移值
        int to = toffset;
        // 特定字符数组
        char pa[] = prefix.value;
        int po = 0;
        // 特定字符数组长度
        int pc = prefix.value.length;
        // Note: toffset might be near -1>>>1.
        // 规则，偏移值不能小于0，并且要小于 待比较长度减去特定字符数组长度后的长度
        if ((toffset < 0) || (toffset > value.length - pc)) {
            return false;
        }
        // 最后字符数组，循环逐个比较，全相等则反馈true
        while (--pc >= 0) {
            if (ta[to++] != pa[po++]) {
                return false;
            }
        }
        return true;
    }
```
### boolean endsWith(String suffix)
- 判断一个字符串是否以特定字符串结尾
- String suffix 特定字符串
```
 public boolean endsWith(String suffix) {
        // 实际上是调用了startswith方法
        // 偏移值是 待比较字符数组长度减去特定字符数组的长度的差值 
        return startsWith(suffix, value.length - suffix.value.length);
    }
```

### int hashCode()
- String 对象的hash值
- hash值的计算实际上是s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
```
    public int hashCode() {
        int h = hash;
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }
```


## AbstractStringBuilder源码阅读


### AbstractStringBuilder append(String str)
- append一个字符串
- 返回的是本身对象，return this
- StringBuffer 和StringBuilder是 AbstractStringBuilder的继承子类，Stringbuffer 给所有方法加上了synchronized,所以是线程安全的

```
    public AbstractStringBuilder append(String str) {
        // 判断str是否为null，如果是null，增加一个null字符串
        if (str == null)
            return appendNull();
        // 待append字符串的长度
        int len = str.length();
        // 确认是否需要扩容
        ensureCapacityInternal(count + len);
        // 数组拷贝
        str.getChars(0, len, value, count);
        // coutn记录 当前字符数量
        count += len;
        return this;
    }
    
    private void ensureCapacityInternal(int minimumCapacity) {
            // overflow-conscious code
            // 判断给定的最小容量 是否大于现在的
            if (minimumCapacity - value.length > 0) {
                value = Arrays.copyOf(value,newCapacity(minimumCapacity));
            }
        }
    
    // Returns a capacity at least as large as the given minimum capacity.
    // 返回一个新的大小，最小情况是给定的大小，一般情况是比这个大
    private int newCapacity(int minCapacity) {
            // overflow-conscious code
            
            // 新的大小等于 现在字符数组的长度 乘以2之后再加2
            // e.g. value.length = 10， newCapacity = 10*2+2=22
            int newCapacity = (value.length << 1) + 2;
            // 如果新的容量小于 给定的大小，则新的容量等于现在的大小
            if (newCapacity - minCapacity < 0) {
                newCapacity = minCapacity;
            }
            return (newCapacity <= 0 || MAX_ARRAY_SIZE - newCapacity < 0)
                ? hugeCapacity(minCapacity)
                : newCapacity;
    }

    private int hugeCapacity(int minCapacity) {
        if (Integer.MAX_VALUE - minCapacity < 0) { // overflow
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE)
            ? minCapacity : MAX_ARRAY_SIZE;
    }    

```