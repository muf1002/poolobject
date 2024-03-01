/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;
/**
 * @author alumno
 *
 */
public class ReusablePoolTest {

	private ReusablePool pool;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		 pool = ReusablePool.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pool = null;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		ReusablePool pool = ReusablePool.getInstance();
        assertNotNull(pool);
        assertTrue(pool instanceof ReusablePool);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() {
	    try {
	        // Primera instancia.
	        Reusable reusable1 = pool.acquireReusable();
	        assertNotNull(reusable1);
	        assertTrue(reusable1 instanceof Reusable);

	        // Segunda instancia.
	        Reusable reusable2 = pool.acquireReusable();
	        assertNotNull(reusable2);
	        assertTrue(reusable2 instanceof Reusable);

	        // Tercera instancia (no hay).
	        pool.acquireReusable(); // Lanza NotFreeInstanceException.

	        // Si no lanza la excepcion falla el test.
	        fail("Se esperaba una excepción NotFreeInstanceException, pero no se lanzó.");
	    } catch (NotFreeInstanceException e) {
	    	// Entra al catch al lanzar la excepción.
	        assertNotNull(e);
	    }
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 * @throws NotFreeInstanceException 
	 */
	@Test
	public void testReleaseReusable() throws NotFreeInstanceException {
		try {
			// Instancias a liberar.
			Reusable reusable1 = pool.acquireReusable();
			assertNotNull(reusable1);
			
			// Liberamos las instancia.
		    pool.releaseReusable(reusable1);
		    
			// Volvemos a liberar la instancia.
			pool.releaseReusable(reusable1);//Lanza DuplicatedInstanceException
			
			// Si no lanza la excepcion falla el test.
			fail("Se esperaba una excepción DuplicatedInstanceException, pero no se lanzó.");
		}catch(DuplicatedInstanceException e){
			assertNotNull(e);
		}
	
	}
	
    @Test
    public void testUtil() {
        Reusable reusable = new Reusable();
        String result = reusable.util();

        assertNotNull(result);
        assertTrue(result.contains(reusable.hashCode() + ""));
        assertTrue(result.contains("Uso del objeto Reutilizable"));
    }

}
