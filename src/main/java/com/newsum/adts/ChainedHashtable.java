package com.newsum.adts;

import com.newsum.model.Employee;

import java.util.LinkedList;
import java.util.ListIterator;

public class ChainedHashtable {

  private LinkedList<StoredEmployee>[] hashtable;

  public ChainedHashtable(){
    hashtable = new LinkedList[10];
    for (int i = 0; i < hashtable.length; i++){
      hashtable[i] = new LinkedList<StoredEmployee>();
    }
  }

  public void put(String key, Employee employee){
    // hash the key
    int hashedKey = hashKey(key);
    hashtable[hashedKey].add(new StoredEmployee(key, employee));
  }

  public Employee get(String key){
    // hash the key
    int hashedKey = hashKey(key);

    // locate StoredEmployee with matching key
    ListIterator<StoredEmployee> iterator = hashtable[hashedKey].listIterator();
    StoredEmployee storedEmployee = null;
    while (iterator.hasNext()){
      storedEmployee = iterator.next();
      if (storedEmployee.key.equals(key)){
        return storedEmployee.employee;
      }
    }
    return null;
  }

  public Employee remove(String key){
    // hash the key
    int hashedKey = hashKey(key);

    // locate StoredEmployee with matching key
    ListIterator<StoredEmployee> iterator = hashtable[hashedKey].listIterator();
    StoredEmployee storedEmployee = null;
    int index = -1;
    while (iterator.hasNext()){
      storedEmployee = iterator.next();
      index++;
      if (storedEmployee.key.equals(key)){
        break;
      }
    }

    // either traversed entire list and did not find matching key, or found matching key
    if (storedEmployee == null) {
      return null;
    } else {
      hashtable[hashedKey].remove(index);
    }

    return storedEmployee.employee;
  }

  /**
   * Hashing function to determine index of backing array
   * @param key
   * @return
   */
  private int hashKey(String key){
    return key.length() % hashtable.length; // must mod by backing array length to return valid array index
  }

  public void printHashtable(){
    for (int i = 0; i < hashtable.length; i++){
      if (hashtable[i].isEmpty()){
        System.out.println("Position "+ i + ": empty");
      } else{
        System.out.print("Position " + i + ": ");
        ListIterator<StoredEmployee> iterator = hashtable[i].listIterator();
        while (iterator.hasNext()){
          System.out.print(iterator.next().employee);
          System.out.print("<->");
        }
        System.out.println("null");
      }
    }
  }

  public static void main(String[] args) {
    Employee janeJones = new Employee("Jane","Jones",123);
    Employee johnDoe = new Employee("John","Doe", 4567);
    Employee marySmith = new Employee("Mary","Smith", 22);
    Employee mikeWilson = new Employee("Mike","Wilson",3245);

    ChainedHashtable ht = new ChainedHashtable();
    ht.put("Jones",janeJones);
    ht.put("Doe",johnDoe);
    ht.put("Wilson",mikeWilson);
    ht.put("Smith",marySmith);

    ht.printHashtable();

    System.out.println("Retrieve key Smith: "+ ht.get("Smith"));

    ht.remove("Doe");
    ht.remove("Jones");
    ht.printHashtable();
    System.out.println("Retrieve key Smith: "+ ht.get("Smith"));
  }
}
