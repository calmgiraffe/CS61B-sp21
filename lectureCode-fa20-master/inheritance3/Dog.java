public class Dog implements Comparable<Dog> {
    public String name;
    private int size;

    public Dog(String n, int s) {
        name = n;
        size = s;
    }

    @Override
    public int compareTo(Dog uddaDog) {
        //assume nobody is messing up and giving us
        //something that isn't a dog.
        return size - uddaDog.size;
    }
    // Approach: create classes that implement the Comparator interface
    // NameComparator is a type of comparator that knows how to compare Dogs
    private static class NameComparator implements Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            // Defer to the String method String.compareTo
            return a.name.compareTo(b.name); 
        }
    }
    private static class SizeComparator implements Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            return a.compareTo(b);
        }
    }
    public static Comparator<Dog> getNameComparator() {
        return new NameComparator();
    }
    public static Comparator<Dog> getSizeComparator() {
        return new SizeComparator();
    }
    public void bark() {
        System.out.println(name + " says: bark");
    }
}