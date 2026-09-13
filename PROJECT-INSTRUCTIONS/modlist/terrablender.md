# TerraBlender

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81eb9fe0ce12f8bc20f3
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `TerraBlender-neoforge-1.21.1-4.1.0.8.jar`, mod id `terrablender`, runtime `4.1.0.8`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, TerraBlender 4.1.0.8 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** TerraBlender
- **Arquivo JAR:** `TerraBlender-neoforge-1.21.1-4.1.0.8.jar`
- **Versão 1.21.1:** 4.1.0.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Worldgen
- **Função:** Biblioteca de composição de biomas/worldgen que fornece regions e integração com MultiNoise/OverworldBiomeBuilder/SurfaceRules para consumers distribuírem biomas de forma compatível.
- **Dependências:** NeoForge 1.21.1. Necessidade depende de consumers que declaram TerraBlender; não adiciona biomas sozinho. Build física 4.1.0.8 é a Beta canônica 1.21.1.
- **Sobreposição:** Não adiciona biomas por si; é infraestrutura para Nature's Spirit/BWG e outros consumidores.
- **Compatibilidade/Riscos:** Region/weight conflicts, bootstrap/init order, cache stale e worldgen seams. 4.1.0.8 corrige parameter list não inicializada; 4.1.0.6 clean cache; 4.1.0.5 init antes de F3 info. Testar todos os consumers atuais.
- **Observações:** mod id `terrablender`; runtime 4.1.0.8 Beta. Decisão Sem decisão preservada. Não atribuir a TerraBlender biomas, blocos ou surface rules concretas de seus consumers.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/source oficial TerraBlender 4.1.0.8 NeoForge 1.21.1. Dossiê de 09/09 preservado; consumers atuais e distribuição real de biomas não foram testados em runtime.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/terrablender-neoforge/files/6054947 ; https://github.com/Glitchfiend/TerraBlender
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — TerraBlender 4.1.0.8 permanece exatamente instalado; regions, MultiNoise/SurfaceRules, bootstrap/cache lifecycle, integrações, riscos e testes preservados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `TerraBlender-neoforge-1.21.1-4.1.0.8.jar`, mod id `terrablender`, versão `4.1.0.8`. TerraBlender é uma **biblioteca de composição de biomas/worldgen**; não adiciona um catálogo próprio de biomas por si só.

## 1. Identidade e canal
- **Mod:** TerraBlender.
- **JAR:** `TerraBlender-neoforge-1.21.1-4.1.0.8.jar`.
- **Mod id:** `terrablender`.
- **Versão:** `4.1.0.8`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal oficial:** Beta; é a build 1.21.1 publicada para NeoForge, não há motivo técnico para trocá-la apenas por causa do rótulo Beta.
- **Decisão:** Sem decisão; preservada.

## 2. Papel arquitetural
O projeto se define como library para mods adicionarem biomas de forma simples e compatível com o sistema moderno de biome/terrain do Minecraft.

TerraBlender fornece a **infraestrutura de mistura/injeção**; o consumer fornece biomes, surface rules e pesos/regions conforme sua implementação.

## 3. Regions e distribuição
A API de TerraBlender trabalha com regiões/participação no biome source para permitir que diferentes mods reservem partes do espaço climático e distribuam seus biomas sem substituir integralmente o gerador de outro mod.

A consequência operacional é que ordem, peso e parametrização dos consumers podem afetar frequência final dos biomas mesmo quando todos carregam sem erro.

## 4. Integração MultiNoise / OverworldBiomeBuilder
O source oficial expõe acesso técnico a `MultiNoiseBiomeSource` e `OverworldBiomeBuilder`, incluindo parâmetros climáticos, oceans, middle/plateau/shattered biomes e métodos de adicionar biomas underground/bottom.

Isto confirma que a library opera na camada de **seleção/composição de biomas**, não na criação de conteúdo decorativo.

## 5. Surface rules
TerraBlender também expõe infraestrutura relacionada a `SurfaceRules`, permitindo que consumers combinem regras de superfície com os biomas que introduzem.

A regra concreta de blocos/solo continua pertencendo ao consumer; TerraBlender não deve ser catalogado como provider de pedra, solo ou vegetação próprios.

## 6. Release 4.1.0.8
Changelog 4.1.0 para 1.21.1:
- 4.1.0.8: corrige **uninitialized parameter list**;
- 4.1.0.7: corrige build em 1.21.1;
- 4.1.0.6: cria clean cache ao gerar biomas;
- 4.1.0.5: garante inicialização antes de aplicar info no F3;
- 4.1.0.2/1: corrige/adiciona debug regions.

Essas mudanças tornam inicialização, cache e debug worldgen regression gates do runtime.

## 7. Client / server
Biome generation é server/world authoritative. Informações F3/debug são client-facing, mas derivam da composição de worldgen já estabelecida.

Dedicated server deve carregar TerraBlender e consumers sem depender de renderer client-only.

## 8. Lifecycle
Validar:
- world creation;
- registry/worldgen bootstrap;
- geração do primeiro chunk;
- pregeneration;
- server restart;
- F3/debug info após init;
- criação de segundo mundo na mesma sessão para detectar cache stale;
- troca/remoção de consumer em cópia de teste.

## 9. Integrações concretas no pack
- **Biolith:** sua ficha no pack documenta compatibilidade com biomas TerraBlender; coexistência precisa preservar distribuição.
- **Connector Extras:** contém bridge relacionada a TerraBlender para determinados cenários; é camada separada.
- **BetterNether: New Dawn/BCLib:** stack de worldgen que pode coexistir com TerraBlender conforme seus consumers.
- **Tectonic/Terralith:** operam em camadas distintas; Tectonic molda terreno, Terralith adiciona worldgen próprio e não deve ser presumido como consumer TerraBlender apenas por coexistir.

## 10. Riscos técnicos
1. **Consumer removal:** biblioteca pode parecer sem conteúdo, mas ser requisito de biome mods.
2. **Region/weight conflicts:** distribuição pode ficar desbalanceada sem crash.
3. **Bootstrap order:** 4.1.0.8 corrige parâmetro não inicializado; startup é regression gate.
4. **Cache stale:** 4.1.0.6 mostra que cache entre gerações é superfície crítica.
5. **Debug mismatch:** F3 region info não deve ser usado como prova única de spawn/distribuição real.
6. **Worldgen seams:** mudar consumers/weights não reescreve chunks antigos.

## 11. Matriz de testes
- [ ] Dedicated server inicia com TerraBlender 4.1.0.8 e consumers atuais.
- [ ] Mundo novo gera sem parameter-list/init crash.
- [ ] Segundo mundo na mesma sessão não reutiliza cache incorreto.
- [ ] F3/debug region info aparece após inicialização.
- [ ] Biomas de consumers TerraBlender efetivos aparecem com distribuição plausível.
- [ ] Pregeneration não gera missing biome/surface rule errors.
- [ ] Restart preserva geração futura consistente.
- [ ] Remoção de consumer em cópia de teste não corrompe worlds existentes silenciosamente.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 12. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão.
- CurseForge oficial TerraBlender NeoForge 4.1.0.8: build Beta 1.21.1 e changelog.
- Repositório oficial Glitchfiend/TerraBlender: propósito da library e integração com MultiNoise/OverworldBiomeBuilder/SurfaceRules.

## 13. Revalidação física — 11/09/2026
A modlist física atual mantém exatamente `TerraBlender-neoforge-1.21.1-4.1.0.8.jar`, mod id `terrablender`, versão `4.1.0.8`. O papel continua sendo infraestrutura de composição de biomas/worldgen; nenhum biome/content próprio foi atribuído indevidamente à library.

Bootstrap, cache entre mundos, F3/debug regions e distribuição efetiva dos consumers continuam sem teste runtime nesta recatalogação.
