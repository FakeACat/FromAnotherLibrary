package mod.acats.fromanotherlibrary.content.config;

import mod.acats.fromanotherlibrary.config.v2.ModConfig;
import mod.acats.fromanotherlibrary.config.v2.SpawnValues;
import mod.acats.fromanotherlibrary.config.v2.properties.*;

public class ExampleConfig extends ModConfig {
    public static final ExampleConfig INSTANCE = new ExampleConfig();
    @Override
    public String name() {
        return "example";
    }

    @Override
    protected int version() {
        return 0;
    }

    public final ArrayProperty exampleArrayProperty = addProperty(new ArrayProperty(
            "example_array",
            "Configurable string array",
            new String[]{
                    "Test 1",
                    "Test 2",
                    "rat"
            },
            false
    ));

    public final BooleanProperty exampleBooleanProperty = addProperty(new BooleanProperty(
            "example_boolean",
            "Configurable boolean",
            false,
            false
    ));

    public final FloatProperty exampleFloatProperty = addProperty(new FloatProperty(
            "example_float",
            "Configurable float with no range limits",
            1.0F,
            false
    ));

    public final IntegerProperty exampleIntegerProperty = addProperty(new IntegerProperty(
            "example_integer",
            "Configurable integer with no range limits",
            1,
            false
    ));

    public final SpawnValuesProperty exampleSpawnValuesProperty = addProperty(new SpawnValuesProperty(
            "example_spawn_values",
            "Configurable spawn values",
            new SpawnValues(
                    true,
                    50,
                    2,
                    5
            )
    ));
}
