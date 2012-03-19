import java.util.LinkedList;
import java.util.List;

import models.Choice;
import models.Poll;

import org.junit.Test;

import api.*;

import play.Play;
import play.test.Fixtures;
import play.test.UnitTest;

public class APITest extends UnitTest {

	@Test
    public void checkAllClassesOfTheAPIPackage() {
		System.out.println(Play.classloader.getAllClasses());
		
    }
}