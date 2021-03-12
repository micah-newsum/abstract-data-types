package com.newsum.adts;

import com.newsum.model.Employee;

public class SimpleHashTable {
  private Employee[] hashTable;

  public SimpleHashTable(){
    hashTable = new Employee[10];
  }

  public void put(String key, Employee employee){
    int hashedKey = hashKey(key);
    // implementation does not handle collisions
    if (hashTable[hashedKey] != null){
      System.out.println("Sorry, there's already an employee at position "+ hashedKey);
    } else {
      hashTable[hashedKey] = employee;
    }
  }

  public Employee get(String key){
    // element can be retrieved in constant time complexity O(1) if key is known.
    int hashedKey = hashKey(key);
    return hashTable[hashedKey];
  }

  public void printHashtable(){
    for (Employee e : hashTable){
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

  public static void main(String args[]){
    SimpleHashTable ht = new SimpleHashTable();
    ht.put("Jones", new Employee("Jane","Jones",1));
    ht.put("Doe",new Employee("John","Doe",2));
    ht.put("Wilson",new Employee("Mike","Wilson",3));

    // will cause collision
    ht.put("Doe",new Employee("Jane","Doe",4));
    ht.printHashtable();

    System.out.println(ht.get("Wilson"));
  }
}
