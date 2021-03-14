package com.newsum.adts;

import com.newsum.model.Employee;

import java.awt.Stroke;

public class SimpleHashTable {
  private StoredEmployee[] hashTable;

  public SimpleHashTable(){
    hashTable = new StoredEmployee[10];
  }

  public void put(String key, Employee employee){
    int hashedKey = hashKey(key);

    if (occupied(hashedKey)) {
      int stopIndex = hashedKey;

      // will loop around array until an empty index is found.
      // linear probing
      if (hashedKey == hashTable.length - 1) {
        hashedKey = 0;
      } else {
        hashedKey++;
      }

      while (occupied(hashedKey) && hashedKey != stopIndex) {
        hashedKey = (hashedKey + 1) % hashTable.length;
      }
    }

    // implementation does not handle collisions
    if (occupied(hashedKey)){
      System.out.println("Sorry, there's already an employee at position "+ hashedKey);
    } else {
      hashTable[hashedKey] = new StoredEmployee(key, employee);
    }
  }

  public Employee remove(String key){
    int hashedKey = findKey(key);
    if (hashedKey == -1){
      return null;
    }

    Employee employee = hashTable[hashedKey].employee;
    hashTable[hashedKey] = null;

    // rehash elements in backing array to avoid problem retrieving subsequent elements
    StoredEmployee[] oldHashtable = hashTable;
    hashTable = new StoredEmployee[oldHashtable.length];
    for (int i = 0; i < oldHashtable.length; i++){
      if ( oldHashtable[i] != null){
        put(oldHashtable[i].key,oldHashtable[i].employee);
      }
    }

    return employee;
  }

  private int findKey(String key){
    int hashedKey = hashKey(key);

    if (hashTable[hashedKey] != null && hashTable[hashedKey].key.equals(key)){
      return hashedKey;
    }

    int stopIndex = hashedKey;

    // will loop around array until an empty index is found.
    // linear probing
    if (hashedKey == hashTable.length - 1) {
      hashedKey = 0;
    } else {
      hashedKey++;
    }

    while (hashedKey != stopIndex && hashTable[hashedKey] != null && !hashTable[hashedKey].key.equals(key)) {
      hashedKey = (hashedKey + 1) % hashTable.length; // hash function with incrementation
    }

    if (hashTable[hashedKey] != null && hashTable[hashedKey].key.equals(key)){
      return hashedKey;
    } else {
      return -1; // hashedKey not found
    }
  }

  public Employee get(String key){
    // element can be retrieved in constant time complexity O(1) if key is known.
    int hashedKey = findKey(key);
    if (hashedKey == -1 ){
      return null;
    }
    return hashTable[hashedKey].employee;
  }

  public void printHashtable(){
    for (StoredEmployee e : hashTable){
      System.out.println(e);
    }
  }

  private int hashKey(String key){
    return key.length() % hashTable.length;
    /**
     * value returned will always be within 0-9 range.
     * h = 1 % 10 = 1
     * he = 2 % 10 = 2
     * hel = 3 % 10 = 3
     * hell = 4 % 10 = 4
     * ...
     * helloworld = 10 % 10 = 0
     */
  }

  private boolean occupied(int index){
    return hashTable[index] != null;
  }

  public static void main(String args[]){
    SimpleHashTable ht = new SimpleHashTable();
    ht.put("Jones", new Employee("Jane","Jones",1));
    ht.put("Doe",new Employee("John","Doe",2));
    ht.put("Wilson",new Employee("Mike","Wilson",3));
    ht.put("Smith",new Employee("Bill","Smith",4)); // will cause collision with Jane Jones because Jones and Smith are the same length.
    System.out.println("Hashtable after adding Employees");
    ht.printHashtable();

    ht.remove("Jones");
    ht.printHashtable();
    System.out.println("Hashtable after removing Jane");
    Employee billSmith = ht.get("Smith");
    System.out.println(billSmith);
  }
}
