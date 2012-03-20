import java.util.LinkedList;
import java.util.List;

import models.Choice;
import models.Poll;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import api.*;
import api.requests.CreatePollRequest;

import play.Play;
import play.test.Fixtures;
import play.test.UnitTest;

public class APITest extends UnitTest {

	@Test
    public void createModels() {
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data.yml");
    }
}