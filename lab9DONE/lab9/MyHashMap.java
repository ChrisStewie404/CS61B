package lab9;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }
    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }
    private int hash(K key,int len){
        if(key==null||len==0) return 0;
        return Math.floorMod(key.hashCode(),len);
    }
    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index=hash(key);
        if(buckets[index]==null||!buckets[index].containsKey(key)) return null;
        else return buckets[index].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        int index=hash(key);
        if(buckets[index]==null){
            buckets[index]=new ArrayMap<>();
            size++;
        }
        if(buckets[index].size()==0) size++;
        buckets[index].put(key,value);
        if(loadFactor()>=MAX_LF){
            resize();
        }
    }
    private void resize(){
        int newlen=buckets.length*2;
        ArrayMap<K,V>[] newbuckets=new ArrayMap[newlen];
        for(int i=0;i<newbuckets.length;i++) newbuckets[i]=new ArrayMap<>();
        for(int i=0;i<buckets.length;i++){
            if(buckets[i]==null||buckets[i].size()==0) continue;
            for(K key:buckets[i].keySet()){
                int newhash=hash(key,newlen);
                newbuckets[newhash].put(key,buckets[i].get(key));
            }
        }
        this.buckets=newbuckets;
    }
    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
