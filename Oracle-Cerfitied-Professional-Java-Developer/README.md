# Oracle Certified Professional Java Developer

This is how I'm preparing for the OCPJP 8 certificate exam

* [Java Class Design](https://github.com/syongxin/Java-Playground/tree/master/Oracle-Cerfitied-Professional-Java-Developer#java-class-design)
    * [Implement Encapsulation](https://github.com/syongxin/Java-Playground/tree/master/Oracle-Cerfitied-Professional-Java-Developer#1-implement-encapsulation)
    * [Implement inheritance including access modifiers and composition](https://github.com/syongxin/Java-Playground/tree/master/Oracle-Cerfitied-Professional-Java-Developer#2-implement-inheritance-including-access-modifiers-and-composition)
    * [Implement Polymorphism](https://github.com/syongxin/Java-Playground/tree/master/Oracle-Cerfitied-Professional-Java-Developer#3-implement-polymorphism)
    * [Override hashCode, equals and toString methods from Object class](https://github.com/syongxin/Java-Playground/tree/master/Oracle-Cerfitied-Professional-Java-Developer#4-override-hashcode-equals-and-tostring-methods-from-object-class)
    * [Create and use singleton classes and immutable classes](https://github.com/syongxin/Java-Playground/tree/master/Oracle-Cerfitied-Professional-Java-Developer#5-create-and-use-singleton-classes-and-immutable-classes)
    * [Develop code that uses static keyword on initialize blocks, variables, methods and classes](https://github.com/syongxin/Java-Playground/tree/master/Oracle-Cerfitied-Professional-Java-Developer#6-develop-code-that-uses-static-keyword-on-initialize-blocks-variables-methods-and-classes)
# Java Class Design

## 1. Implement Encapsulation

*Encapsulation is a mechanism to wrap data (variables) and code acting on the date (methods) together as a single unit.*

**Encapsulation example**:

    public class Data {
    	private int value;
    
    	public int getValue() {
    		return this.value;
    	}
    	
    	public void setValue(int v) {
    		this.value = v;
    	}
    }

**Benefits**:

- Allowing implementation details of an object to be modified without breaking existing clients.
- Protecting an object's internal state from vulnerable situations such as being accessed by malicious code or set to an invalid value.

## 2. Implement inheritance including access modifiers and composition

*Inheritance is a mechanism allowing one type to acquire, or inherit, fields and methods from another, it implements an IS-A relationship.*

**Access modifiers**:

![AccessModifiers](https://www.cdn.geeksforgeeks.org/wp-content/uploads/Access-Modifiers-in-Java.png)

**Inheritance example**:

    // Superclass declaration
    public class Super {
    	protected String text = "Super";
    	
    	protected void print(String str) {
    		System.out.println(str);
    	}
    }
    
    // Sbuclass declaration
    public class Sub extends Super {}
    
    // Main app
    public class App {
    	public static void main(String[] args) {
    		Sub sub = new Sub();
    		// output "Super"
    		sub.print(sub.text);
    	}
    }

**Composition**:

*Composition is another mechanisms for code reuse in Java, it implements a HAS-A relationship between classes.*

Composition is designed for situation where you want a class to selectively reuse some of the public methods of another class.

**Composition example**:

    // the "backend class"
    public class MainOffice {
    	private String address;
    	public MainOffice(String address){
    		this.address = address;
    	}
    
    	public String getAddress() {
    		return address;
    	}
    	// ... other stuff
    }
    
    // the "frontend" class
    public class Company {
    	private MainOffice mainOffice;
    	private List<Branch> branches;
    	
    	public Company(MainOffice mainOffice) {
    		this.mainOffice = mainOffice;
    	}
    	
    	public String mainAddress() {
    		// method from the composited class
    		return mainOffice.getAddress();
    	}
    	// ... other stuff
    }

## 3. Implement Polymorphism

*Polymorphism is the ability for a method to behave differently based on the actual objects it is invoked on at runtime.*

**Polymorphism example**:

    public class Super {
    	protected void print() {}
    }
    
    public class Sub1 extends Super {
    	@Override
    	public void print() {
    		System.out.println("Sub1");
    	}
    }
    
    public class Sub2 extends Super {
    	@Override
    	public void print() {
    		System.out.println("Sub2");
    	}
    }
    
    public class App {
    	public static void main(String[] args) {
    		Super obj1 = new Sub1();
    		Super obj2 = new Sub2();
    		// output Sub1
    		obj1.print();
    		// output Sub2
    		obj2.print();
    	}
    }

## 4. Override hashCode, equals and toString methods from Object class

### hashCode

- *hashCode* method of the root class *Object* typically returns the object's memory address in hexadecimal.
- The hash code of an object, returned by hashCode, is used as the key referencing a bucket in a HashSet, HashMap or HashTable.
- If two objects are equal according to the *equals* method, their hash code must also be equal; if you override the *equals* method, you must also override *hashCode* method as well
- Method signature: `public int hashCode()`

### equals

- The *equals* method of the root class *Object* uses the identity operator, `==`, to determine if two objects are equal; in other words, it returns true if and only if the objects compared are exactly the same
- You must override the *equals* method to compare two objects for equality in a meaningful way
- Method signature: `public boolean equals(Object o)`

### toString

- The *toString* method of the root class *Object* returns a *String* equal to the value of this expression: `getClass().getName() + '@' + Integer.toHexString(hashCode())`
- When overriding the *toString* method, you have a method returning a *String* representation of an object, which is usually used for logging or debugging
- Method signature: `public String toString()`

## 5. Create and use singleton classes and immutable classes

### Singleton class

- A Singleton class is a class that allows only on instance to be created
- A `private` constructor that prevents the class from being instantiated from outside
- A `public static` method returning the only object of the class
- Implementations: Lazy initialization; Initialization-on-demand holder idiom; Eager initialization; Using enum

**Lazy initialization**

    public class Singleton {
    	private static Singleton INSTANCE;
    	// private constructor
    	private Singleton() {}
    	// thread-safe, public static instance getter
    	public synchronized static Singleton getInstance() {
    		if (INSTANCE == null) {
    			INSTANCE = new Singleton();
    		}
    		return INSTANCE;
    	}
    }

**Initialization-On-Demand Holder Idiom**

Mostly used method in Java

    public class Singleton {
    	private Singleton() {}
    	// inner holder class
    	private static class SingletonHolder {
    		private static final Singleton INSTANCE = new Singleton();
    	}
    	public static Singleton getInstance() {
    		return SingletonHolder.INSTANCE;
    	}
    }

**Eager Initialization**

    public class Singleton {
    	private static final Singleton INSTANCE = new Singleton();
    
    	private Singleton() {}
    
    	public static Singleton getInstance() {
    		return INSTANCE;
    	}
    }

**Enum**

    public enum Singleton {
    	INSTANCE
    }

### Immutable class

- A class that the state of its objects cannot be modified after they are created
- Creating immutable classes:
    - Make all fields `final` and `private`
    - No setter methods
    - Do not allow subclasses to override methods of the class you want to make immutable
    - If instance fields of the class include references to mutable objects, do not directly expose those objects
- Pros: Objects of an immutable class cannot be corrupted by thread interference of observed in an inconsistent state
- Cons: A new object must be created each time you need an object with a different state, which may result in object proliferation

    public final class Person {
    	private final String fullName;
    	private final List<String> nickNames;
    
    	public Person(String fullName, List<String>nickNames) {
    		this.fullName = fullName;
    		// stores a copy
    		this.nickNames = new ArrayList<>(nickNames);
    	}
    
    	public String getFullName() {
    		return fullName;
    	}
    
    	public List<String> getNickNames() {
    		// returns a copy
    		return new ArrayList<>(nickNames);
    	}
    }

## 6. Develop code that uses static keyword on initialize blocks, variables, methods and classes

- The `static` keyword is declared on class members that are associated with the class itself rather than with any instance object
- Fields that have the `static` modifier in their declaration are static fields, also called class variables
- Methods that have the `static` modifier in their declaration are static methods, also called class methods `<modifier> static MyReturnType myClassMethod(){}` These methods should be invoked with the class name, without the need for creating an instance of the class

> Static methods cannot access instance variables or call instance methods directly, they must use object references

- A static initialization block is a normal block of code enclosed in curly braces, {}, preceded by the static keyword `static { // something }` Static blocks are called once when the class is loaded into the memory, in the order that they appear in the source code
- A static class must be declared inside another class and has access to static members of that enclosing class, including private members.

        public class Outer {
        	private static String outerVar;
        	private String outerInstanceVar;
        
        	public static class Nested {
        		private String innnerVar = outerVar;
        	}
        }

    A static class cannot refer directly to instance variables or methods of its containing class; instead, it must use object references to access those members:

    `private String innerInstanceVar = new Outer().outerInstanceVar;`

    Static nested classes are referenced from outside of the outer class using the name of such a class:

    `Outer.Nested nestedObject = new Outer.Nested();`
