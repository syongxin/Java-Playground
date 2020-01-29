# Oracle Certified Professional Java Developer

This is how I prepared for the OCPJP 8 certificate exam

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
