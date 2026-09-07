# Autoridade de versão para skills e instruções

## Alvo

- Minecraft: **1.21.1**
- NeoForge: **21.1.x**
- Java: **21**

A versão exata das dependências deve ser lida do build, metadata e modlist física atual.

## Ordem de autoridade

Para classe, método, evento, registry, resource path, pack format, assinatura, comportamento de provider ou compatibilidade:

1. código/build/metadata realmente presentes no repositório;
2. JAR/modlist física mais recente;
3. source/JAR/documentação oficial da versão exata;
4. decisões e instruções canônicas do projeto;
5. skill específica do projeto;
6. skill em `library/`;
7. exemplos de outras versões apenas como referência conceitual.

## Fail-closed

Se algo version-sensitive não puder ser confirmado para Minecraft 1.21.1 / NeoForge 21.1.x:

- não inventar API, signature, registry, path ou comportamento;
- não transplantar silenciosamente exemplo de versão posterior, Fabric, Forge legado ou Paper;
- procurar evidência no código/JAR/documentação disponível;
- registrar a incerteza ou pendência;
- manter integração opcional indisponível/fail-closed quando necessário.

## Recursos e providers

Skills multi-versão podem conter `pack_format`, resource paths, components e APIs posteriores a 1.21.1. O agente deve provar o formato correto antes de gerar ou validar conteúdo. GeckoLib, Photon, AAA Particles/Effekseer e outros providers devem ser tratados segundo a versão realmente instalada; presença na modlist não prova que uma API recente exista nessa build.
