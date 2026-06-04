package prog.hei.app.file.hash;

import prog.hei.app.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
