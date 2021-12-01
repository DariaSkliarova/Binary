import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try {
            new Main().exe();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void exe() throws IOException, ClassNotFoundException {
        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream("data.bin"))) {
            stream.writeInt(1);
            stream.writeUTF("December");
            stream.writeLong(2021);
        }

        System.out.println("\n       Reading File from Previous Task");
        try (
                DataInputStream stream = new DataInputStream(new FileInputStream("text3.txt"))) {
            int nums = stream.readInt();
            System.out.println(nums);
        }

        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream("lines.bin"))) {
            stream.writeUTF("""
                    From fairest creatures we desire increase,
                    That thereby beauty’s rose might never die,
                    But as the riper should by time decrease,
                    His tender heir mught bear his memeory:
                    But thou, contracted to thine own bright eyes,
                    Feed’st thy light’st flame with self-substantial fuel,
                    Making a famine where abundance lies,
                    Thyself thy foe, to thy sweet self too cruel.
                                           By William Shakespeare""");
        }

        System.out.println("\n       Lines from File");
        try (DataInputStream stream = new DataInputStream(new FileInputStream("lines.bin"))) {
            String lines = stream.readUTF();
            System.out.println(lines);
        }

        List<Contact> contacts = new ArrayList<>();
        Contact contact1 = new Contact();
        contact1.setName("Michael");
        contact1.setSurname("Douglas");
        contact1.setTelNumber("0504242288");
        contact1.setDate("01.01.1950");
        Contact contact2 = new Contact();
        contact2.setName("Fred");
        contact2.setSurname("Krueger");
        contact2.setTelNumber("0991267788");
        contact2.setDate("05.09.1969");
        Contact contact3 = new Contact();
        contact3.setName("Linda");
        contact3.setSurname("Perry");
        contact3.setTelNumber("0638975747");
        contact3.setDate("25.11.1972");
        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);

        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream("Contacts.bin"))) {
            for (Contact contact : contacts) {
                stream.writeUTF(contact.getName() + " | " + contact.getSurname() + " | "
                        + contact.getTelNumber() + " | " + contact.getDate());
            }
        }

        List<Contact> contactsNew = new ArrayList<>();

        System.out.println("\n       ArrayList from Binary File");
        try (DataInputStream stream = new DataInputStream(new FileInputStream("Contacts.bin"))) {
            while (stream.available() > 0) {
                String line = stream.readUTF();
                Contact contact = new Contact();
                String[] params = line.split("\\|");
                contact.setName(params[0]);
                contact.setSurname(params[1]);
                contact.setTelNumber(params[2]);
                contact.setDate(params[3]);
                contactsNew.add(contact);
            }
            for (Contact contact : contactsNew) {
                System.out.println(contact.getName() + " | " + contact.getSurname() + " | "
                        + contact.getTelNumber() + " | " + contact.getDate());
            }
        }

        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("Contacts.dat"))) {
            stream.writeObject(contacts);
        }

        System.out.println("\n       Deserialized ArrayList");
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream("Contacts.dat"))) {
            List<Contact> contactsDeserialized = (ArrayList<Contact>) stream.readObject();
            for (Contact contact : contactsDeserialized) {
                System.out.println(contact.getName() + " | " + contact.getSurname() + " | "
                        + contact.getTelNumber() + " | " + contact.getDate());
            }
        }


        int fileCount = 0;
        for (Path p : Files.walk(Path.of("C:\\Program Files\\JetBrains")).collect(Collectors.toSet())) {
            if (!(p.toFile().isDirectory()))
                fileCount++;
        }
        System.out.println("\nNumber of files: " + fileCount);
    }
}
