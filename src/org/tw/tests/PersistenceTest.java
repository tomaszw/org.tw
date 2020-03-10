/*
 * Created on 2005-08-30
 */

package org.tw.tests;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;

import junit.framework.TestCase;

import org.tw.persistence.xml.ClassidBindings;
import org.tw.persistence.xml.Session;
import org.tw.persistence.xml.SessionFactory;
import org.tw.persistence.xml.annotations.PersistAs;
import org.tw.web.XmlKit;
import org.w3c.dom.Document;

public class PersistenceTest extends TestCase {
  static class Dog {
    @PersistAs("age")
    public int    age;

    @PersistAs("children")
    public Dog[]  children;

    //@PersistAs("race")
    public String dograce = "";

    public Dog() {
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null)
        return false;
      if (!obj.getClass().equals(getClass()))
        return false;
      Dog other = (Dog) obj;
      return age == other.age && dograce.equals(other.dograce)
          && Arrays.equals(children, other.children);
    }
  }

  static class Node {
    @PersistAs("next")
    Node next;

    @PersistAs("value")
    int  value;

    public Node() {
    }
  }

 
  SessionFactory fac = new SessionFactory();
  
  ClassidBindings newBindings() {
    ClassidBindings b = new ClassidBindings();
    b.add("dog", Dog.class);
    b.add("nodenodenode", Node.class);
    return b;
  }
  
  
  @Override
  protected void setUp() throws Exception {
    fac.addBindings(newBindings());
  }


  Session newSaveSession(Document doc) {
    //ClassidBindings b = ClassidBindings.fromAnnotations(Node.class, Dog.class);
    //fac.addBindings(b);
    Session ses =  (Session ) fac.newSaveSession(doc);
    return ses;
  }

  Session newLoadSession() {
    //ClassidBindings b = ClassidBindings.fromAnnotations(Node.class, Dog.class);
    //fac.addBindings(b);
    Session ses =  (Session ) fac.newLoadSession();
    return ses;
  }
  
  public void testSimpleStore() {
    Dog dog = new Dog();
    dog.age = 10;
    dog.dograce = "Rasa1";

    Document doc = XmlKit.createDoc();
    Session ses = newSaveSession(doc);
    doc.appendChild(ses.store(dog));
    ses.close();
    
    ses = newLoadSession();
    Dog dog2 = (Dog) ses.load(doc.getDocumentElement());
    assertTrue(dog.equals(dog2));
    
    XmlKit.saveDoc("testSimpleStore.xml", doc);
  }

  public void testButtonStore() {
    Document doc = XmlKit.createDoc();
    Session ses = newSaveSession(doc);
    doc.appendChild(ses.store(new JButton("Hello, world!")));
    ses.close();
    XmlKit.saveDoc("testButtonStore.xml", doc);
  }
  
  public void testArrayStore() {
    Dog dog = new Dog();
    dog.age = 10;
    dog.dograce = "Rasa1";
    dog.children = new Dog[2];
    dog.children[0] = new Dog();
    dog.children[0].age = 3;
    dog.children[1] = new Dog();
    dog.children[1].age = 4;

    Document doc = XmlKit.createDoc();
    Session ses = newSaveSession(doc);
    doc.appendChild(ses.store(dog));
    ses.close();
    ses = newLoadSession();
    Dog dog2 = (Dog) ses.load(doc.getDocumentElement());
    assertTrue(dog.equals(dog2));

    XmlKit.saveDoc("testArrayStore.xml", doc);
  }

  public void testArrListStore() {
    ArrayList<Integer> l = new ArrayList<Integer>();
    l.add(1);
    l.add(2);
    l.add(3);
    Document doc = XmlKit.createDoc();
    Session ses = newSaveSession(doc);
    doc.appendChild(ses.store(l));
    ses.close();
    
    XmlKit.saveDoc("testArrayListStore.xml", doc);

    ses = newLoadSession();
    ArrayList<Integer> l2 = (ArrayList<Integer>) ses.load(doc.getDocumentElement());
    assertTrue(l.equals(l2));
    
    
    
  }
  
  public void testCircularListStore() {
    Node head = new Node();
    Node node = head;
    for (int i = 0; i < 3; ++i) {
      node.value = i;
      if (i != 2)
        node.next = new Node();
      else {
        node.next = head;
      }
      node = node.next;
    }

    Document doc = XmlKit.createDoc();
    Session ses = newSaveSession(doc);
    doc.appendChild(ses.store(head));
    ses.close();
    XmlKit.saveDoc("testCircularListStore.xml", doc);
    
    ses = newLoadSession();
    Node h2 = (Node) ses.load(doc.getDocumentElement());
    assertEquals(h2.value, 0);
    assertEquals(h2.next.value, 1);
    assertEquals(h2.next.next.value, 2);
    assertEquals(h2.next.next.next, h2);
  }
}
