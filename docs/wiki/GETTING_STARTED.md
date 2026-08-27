# Começando

## Plataforma

- Minecraft 1.21.1
- NeoForge
- Java 21

Use sempre as versões declaradas pelo build do repositório para desenvolvimento e teste.

## Para jogadores

1. Instale o mod e suas dependências obrigatórias.
2. Mods de integração são opcionais conforme o empacotamento/configuração do projeto; não assuma que uma integração documentada transforma o mod externo em dependência obrigatória.
3. Entre em um mundo e deixe o servidor determinar o estado de progressão.
4. Consulte a árvore/UI para requisitos e unlocks disponíveis.

## Para modpack authors

O conteúdo é fortemente data-driven. Ao alterar skills, requisitos ou especializações, valide o datapack inteiro e preserve IDs usados em saves existentes.

## Para desenvolvedores

Com Java 21 configurado, os comandos de referência do projeto são:

```bash
./gradlew test
./gradlew runGameTestServer
./gradlew build
```

No Windows, use `gradlew.bat` quando apropriado.