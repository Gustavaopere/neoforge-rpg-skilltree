package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialBlock;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialContent;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Pure UI projection for Stage 10.10 editorial prose, kept separate from technical facts. */
public record CompendiumEditorialDisplayModel(String title, List<DisplayBlock> blocks) {
    public CompendiumEditorialDisplayModel {
        title = requireText(title, "title");
        blocks = List.copyOf(Objects.requireNonNull(blocks, "blocks"));
        for (DisplayBlock block : blocks) Objects.requireNonNull(block, "block");
    }

    public static CompendiumEditorialDisplayModel from(CompendiumPageModel page) {
        Objects.requireNonNull(page, "page");
        if (page.editorialContent().isEmpty()) {
            return new CompendiumEditorialDisplayModel(page.displayName(), List.of());
        }

        CompendiumEditorialContent editorial = page.editorialContent().orElseThrow();
        ArrayList<DisplayBlock> blocks = new ArrayList<>(editorial.sections().size() + 1);
        blocks.add(displayBlock("summary", editorial.summary()));
        for (CompendiumEditorialSection section : editorial.sections()) {
            blocks.add(displayBlock(section.sectionId(), section.block()));
        }
        return new CompendiumEditorialDisplayModel(editorial.title(), blocks);
    }

    private static DisplayBlock displayBlock(String sectionId, CompendiumEditorialBlock block) {
        return new DisplayBlock(
            sectionId,
            block.text(),
            block.sources().stream().map(source -> source.ref()).toList()
        );
    }

    public record DisplayBlock(String sectionId, String text, List<String> sourceRefs) {
        public DisplayBlock {
            sectionId = requireText(sectionId, "sectionId");
            text = requireText(text, "text");
            sourceRefs = List.copyOf(Objects.requireNonNull(sourceRefs, "sourceRefs"));
            if (sourceRefs.isEmpty()) throw new IllegalArgumentException("sourceRefs must not be empty");
            for (String sourceRef : sourceRefs) requireText(sourceRef, "sourceRef");
        }
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
