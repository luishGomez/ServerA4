package ejb;

import encriptaciones.Encriptador;
import entity.Apunte;
import entity.Cliente;
import entity.Compra;
import entity.CompraId;
import exception.CreateException;
import exception.DeleteException;
import exception.ResumirException;
import exception.SelectCollectionException;
import exception.SelectException;
import exception.UpdateException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mensajeria.EmailThread;

/**
 * La clase que se encarga de la logica de los <b>clientes</b> de la aplicacion.
 * @author Ricardo Peinado Lastra
 */
@Stateless
public class ClienteEJB implements ClienteEJBLocal{
    private static final Logger LOGGER = Logger.getLogger("ServerA4.service.ClienteEJB");
    private Encriptador encriptador=new Encriptador();
    
    /**
     * El objeto Entity Manager
     */
    @PersistenceContext(unitName = "ServerA4PU")
    private EntityManager em;
    
    /**
     * Crea un cliente.
     * @param cliente El objeto {@link Cliente} que contiene los datos.
     * @throws CreateException Salta si ocurre un error al crear.
     */
    @Override
    public void createCliente(Cliente cliente) throws CreateException {
        try{
            cliente.setContrasenia(encriptador.resumir(cliente.getContrasenia()));
            em.persist(cliente);
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> createCliente() "+e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
    /**
     * Edita un cliente ya existente.
     * @param cliente El objeto {@link Cliente} con todos los datos para editar.
     * @throws UpdateException Salta si ocurre un error al modificar.
     */
    @Override
    public void editCliente(Cliente cliente) throws UpdateException {
        try{
            em.merge(cliente);
            em.flush();
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> editCliente() "+e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Borra un cliente ya existente.
     * @param idCliente El ID de un {@link Cliente}.
     * @throws DeleteException Salta si ocurre un error al borrar un cliente.
     */
    @Override
    public void removeCliente(Integer idCliente) throws DeleteException {
        try{
            em.remove(em.merge(em.find(Cliente.class, idCliente)));
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> removeCliente() "+e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
    /**
     * Busca un cliente ya existente por su ID.
     * @param idCliente El ID de un {@link Cliente}.
     * @return Retorna un {@link Cliente}.
     * @throws SelectException Salta si ocurre un error al buscar.
     */
    @Override
    public Cliente findCliente(Integer idCliente) throws SelectException {
        Cliente resultado=null;
        try{
            resultado=em.find(Cliente.class, idCliente);
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> findCliente() "+e.getMessage());
            throw new SelectException(e.getMessage());
        }
        return resultado;
    }
    /**
     * Busca todos los clientes existentes.
     * @return Retorna una lista de {@link Cliente}.
     * @throws SelectCollectionException Salta si ocurre un error al buscar varios datos.
     */
    @Override
    public Set<Cliente> findAllClientes() throws SelectCollectionException {
        /*
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
        */
        Set <Cliente> resultado=null;
        try{
            resultado= new HashSet<>(em.createNamedQuery("findAllCliente").getResultList());
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> findAllClientes() "+e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return resultado;
    }
    
    
    /**
     * Busca los clientes que han votado un {@link Apunte}.
     * @param id El ID de un {@link Apunte}.
     * @return Retorna una lista de {@link Cliente}.
     * @throws SelectCollectionException Salta si ocurre un error en la busqueda de varios datos.
     */
    @Override
    public List <Cliente> getVotantesId(Integer id) throws SelectCollectionException{
        List <Cliente> resultado=null;
        try{
            resultado= (List <Cliente>)em.createNamedQuery("getVotantesId").setParameter("idApunte", id).getResultList();
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> getVotantesId() "+e.getMessage());
            throw new SelectCollectionException(e.getMessage());
        }
        return resultado;
    }
    
    
    /**
     * Actualiza la contraseña de un cliente.
     * @param cliente Envia el objeto de un {@link Cliente}
     * @throws UpdateException Salta si ocurre un error al modificar.
     */
    @Override
    public void actualizarContrasenia(Cliente cliente) throws UpdateException{
        try{
            String contra=cliente.getContrasenia();
            cliente.setContrasenia(encriptador.resumir(cliente.getContrasenia()));
            em.merge(cliente);
            em.flush();
            EmailThread emailT = new EmailThread(cliente.getEmail(),"Contraseña actualizada","Buenas "+cliente.getNombreCompleto()+", le informamos el cambio de contraseña que acaba de hacer. La nueva contraseña es: "+contra);
            emailT.start();
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> actualizarContrasenia() "+e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Permite al {@link Cliente} comprar un {@link Apunte}.
     * @param cliente El objeto que contiene los datos de cliente.
     * @param idApunte El identificador del de apunte.
     * @throws CreateException Salta si ocurre un error al crear.
     */
    @Override
    public void comprarApunte(Cliente cliente, Integer idApunte) throws CreateException{
        try{
            Cliente clienteRenovado = em.find(Cliente.class, cliente.getId());
            Compra nuevaCompra= new Compra();
            Apunte elApunte=em.find(Apunte.class, idApunte);
            nuevaCompra.setApunte(elApunte);
            nuevaCompra.setPropietario(clienteRenovado);
            Date fecha=new Date();
            nuevaCompra.setFecha(fecha);
            nuevaCompra.setIdCompra(new CompraId(clienteRenovado.getId(),idApunte));
            em.persist(nuevaCompra);
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> comprarApunte() "+e.getMessage());
            throw new CreateException(e.getMessage());
        }
        
    }
    /**
     * Renueva automaticamente la contraseña de un {@link Cliente}.
     * @param cliente El objeto contiene los datos de cliente.
     * @throws UpdateException Salta si ocurre un error al actualizar.
     */
    @Override
    public void passwordForgot(Cliente cliente) throws UpdateException{
        int numero;
        String frase ="";
        for(int i=0;i<12;i++){
            numero=(int) Math.floor(Math.random()*(133-97)+97);
            if(numero>=97 && numero<=122){
                frase+=(char)numero;
            }else{
                frase+=(char) (numero-75);
            }
        }
        try {
            cliente.setContrasenia(encriptador.resumir(frase));
            em.merge(cliente);
            em.flush();
            EmailThread emailT = new EmailThread(cliente.getEmail(),"Contraseña provisional, por el olvida de ella.","Buenas "+cliente.getNombreCompleto()+",  hemos recivido vuestra petición de contraseña olvidada, por tanto se la hemos cambiado por la contraseña que a parece a continuación: "+frase);
            emailT.start();
        }catch(ResumirException e){
            LOGGER.severe("ClienteEJB -> passwordForgot() Error al resumir "+e.getMessage());
            throw new UpdateException(e.getMessage());
        }catch (Exception e){
            LOGGER.severe("ClienteEJB -> passwordForgot() "+e.getMessage());
            throw new UpdateException(e.getMessage());
        }
         
    }
}
