import generated.CommType;
import generated.GreetingListType;
import generated.GreetingType;
import generated.ObjectFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Created by vdBerg on 1/20/17.
 */
public class Hello {
    private ObjectFactory of;
    private GreetingListType grList;
    private CommType commType;


    public static void main(String [] args ){

//        referentie: https://jaxb.java.net/tutorial/section_1_3-Hello-World.html#Hello World

        System.out.println("hoi");

        Hello hello = new Hello();
        hello.make("Bonjour madame", "French");
        hello.make("Hello Lady", "Englisch");
//        hello.marshal();
        hello.makeComm();
        hello.marshal2();

    }




    public Hello(){
        of = new ObjectFactory();
        grList = of.createGreetingListType();

        commType = of.createCommType();


    }

    public void make( String t, String l ){
        GreetingType g = of.createGreetingType();
        g.setText( t );
        g.setLanguage( l );
        g.setShortname("45345345345");
        grList.getGreeting().add( g );
    }

    public void makeComm (){
        CommType ct = of.createCommType();
        ct.setEmail("evert@vandenbergonline.eu");

        ct.setSMS("0653865667");
        commType = ct;
    }

    public void marshal() {
        try {
            JAXBElement<GreetingListType> gl = of.createGreetings( grList );
            JAXBContext jc = JAXBContext.newInstance( of.getClass() );
            Marshaller m = jc.createMarshaller();

            m.marshal( gl, System.out );
        } catch( JAXBException jbe ){
            // ...
            System.out.println("something wend wrong:" + jbe.getMessage());
        }
    }


    public void marshal2(){


        Schema mySchema;
        SchemaFactory sf =
                SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
        try {
            mySchema = sf.newSchema( new File("src/main/resources/person.xsd"));
        } catch( SAXException saxe ){
            // ...(error handling)
            System.out.println("er is iets mis gegaan: " + saxe.getMessage() + " " + saxe.getException());
            mySchema = null;
        }



        try {
            JAXBElement<CommType> ct = of.createComm( commType );
            JAXBContext jc = JAXBContext.newInstance( of.getClass() );
            Marshaller m = jc.createMarshaller();
            m.setSchema(mySchema);

            m.marshal( ct, System.out );
        } catch( JAXBException jbe ){
            // ...
            System.out.println("something wend wrong:" + jbe.getMessage());
        }
    }
}
