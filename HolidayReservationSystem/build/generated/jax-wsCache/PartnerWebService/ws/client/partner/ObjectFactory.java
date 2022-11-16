
package ws.client.partner;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.client.partner package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InvalidLoginCredentialException_QNAME = new QName("http://ws.session.ejb/", "InvalidLoginCredentialException");
    private final static QName _Persist_QNAME = new QName("http://ws.session.ejb/", "persist");
    private final static QName _PersistResponse_QNAME = new QName("http://ws.session.ejb/", "persistResponse");
    private final static QName _RetrievePartnerByEmailAndPassword_QNAME = new QName("http://ws.session.ejb/", "retrievePartnerByEmailAndPassword");
    private final static QName _RetrievePartnerByEmailAndPasswordResponse_QNAME = new QName("http://ws.session.ejb/", "retrievePartnerByEmailAndPasswordResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.client.partner
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InvalidLoginCredentialException }
     * 
     */
    public InvalidLoginCredentialException createInvalidLoginCredentialException() {
        return new InvalidLoginCredentialException();
    }

    /**
     * Create an instance of {@link Persist }
     * 
     */
    public Persist createPersist() {
        return new Persist();
    }

    /**
     * Create an instance of {@link PersistResponse }
     * 
     */
    public PersistResponse createPersistResponse() {
        return new PersistResponse();
    }

    /**
     * Create an instance of {@link RetrievePartnerByEmailAndPassword }
     * 
     */
    public RetrievePartnerByEmailAndPassword createRetrievePartnerByEmailAndPassword() {
        return new RetrievePartnerByEmailAndPassword();
    }

    /**
     * Create an instance of {@link RetrievePartnerByEmailAndPasswordResponse }
     * 
     */
    public RetrievePartnerByEmailAndPasswordResponse createRetrievePartnerByEmailAndPasswordResponse() {
        return new RetrievePartnerByEmailAndPasswordResponse();
    }

    /**
     * Create an instance of {@link Partner }
     * 
     */
    public Partner createPartner() {
        return new Partner();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidLoginCredentialException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InvalidLoginCredentialException }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "InvalidLoginCredentialException")
    public JAXBElement<InvalidLoginCredentialException> createInvalidLoginCredentialException(InvalidLoginCredentialException value) {
        return new JAXBElement<InvalidLoginCredentialException>(_InvalidLoginCredentialException_QNAME, InvalidLoginCredentialException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Persist }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Persist }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "persist")
    public JAXBElement<Persist> createPersist(Persist value) {
        return new JAXBElement<Persist>(_Persist_QNAME, Persist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersistResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PersistResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "persistResponse")
    public JAXBElement<PersistResponse> createPersistResponse(PersistResponse value) {
        return new JAXBElement<PersistResponse>(_PersistResponse_QNAME, PersistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrievePartnerByEmailAndPassword }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetrievePartnerByEmailAndPassword }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "retrievePartnerByEmailAndPassword")
    public JAXBElement<RetrievePartnerByEmailAndPassword> createRetrievePartnerByEmailAndPassword(RetrievePartnerByEmailAndPassword value) {
        return new JAXBElement<RetrievePartnerByEmailAndPassword>(_RetrievePartnerByEmailAndPassword_QNAME, RetrievePartnerByEmailAndPassword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrievePartnerByEmailAndPasswordResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetrievePartnerByEmailAndPasswordResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "retrievePartnerByEmailAndPasswordResponse")
    public JAXBElement<RetrievePartnerByEmailAndPasswordResponse> createRetrievePartnerByEmailAndPasswordResponse(RetrievePartnerByEmailAndPasswordResponse value) {
        return new JAXBElement<RetrievePartnerByEmailAndPasswordResponse>(_RetrievePartnerByEmailAndPasswordResponse_QNAME, RetrievePartnerByEmailAndPasswordResponse.class, null, value);
    }

}
