package mod.acats.fromanotherlibrary.config.v2;

public record SpawnValues(boolean enabled,
                          int weight,
                          int groupMin,
                          int groupMax) {
    public SpawnValues setEnabled(boolean bl) {
        return new SpawnValues(bl, weight, groupMin, groupMax);
    }

    public SpawnValues setWeight(int i) {
        return new SpawnValues(enabled, i, groupMin, groupMax);
    }

    public SpawnValues setMin(int i) {
        return new SpawnValues(enabled, weight, i, groupMax);
    }

    public SpawnValues setMax(int i) {
        return new SpawnValues(enabled, weight, groupMin, i);
    }
}
