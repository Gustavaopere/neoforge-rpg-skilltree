# Simply More x Excalibur

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81b080aef0c78de2735b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Simply More x Excalibur.zip`
- **Versão própria:** não publicada; não usar a versão do jogo como versão do resource pack
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Simply More x Excalibur.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física acessível de 08/09/2026 confirma o provider-alvo `simplymore-forge-1.3.0_alpha.jar`, mod id `simplymore`, runtime `1.3.0_alpha`. O artefato foi previamente fechado como Alpha 5 por proveniência externa, mas o metadata runtime permanece `1.3.0_alpha`.

## Propriedades do banco

- **Mod:** Simply More x Excalibur
- **Arquivo JAR:** `Simply More x Excalibur.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** sem versão semântica própria publicada
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, RPG
- **Função:** Compatibility resource pack não oficial que adapta as armas do Simply More à estética Excalibur, incluindo as espadas de materiais-base publicadas pelo projeto.
- **Dependências:** Excalibur base + Simply More 1.3.0_alpha. Não substitui nem cobre automaticamente Simply Swords; cada mod mantém seus próprios assets e gameplay.
- **Sobreposição:** Compete com outros retextures/models de Simply More por prioridade. Simply Swords x Excalibur é pack distinto e não deve ser tratado como equivalente.
- **Compatibilidade/Riscos:** Sem versão semântica própria publicada. O mesmo ZIP é distribuído para várias versões; compatibilidade com Simply More 1.3.0_alpha exige QA. Pode colidir com outros retextures de Simply More.
- **Observações:** Arquivo instalado `Simply More x Excalibur.zip`; o projeto não publica versão semântica própria, portanto `Versão 1.21.1` permanece vazia. Cobertura confirmada inclui iron, gold, diamond, netherite e runic base swords.
- **Procedência:** CurseForge oficial Simply More x Excalibur + captura Resource Packs do perfil em 08/09/2026 + modlist física Simply More 1.3.0_alpha.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/simply-more-x-excalibur
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossier visual reconstruído; Simply More 1.3.0_alpha, escopo material-base, authority, load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Simply More x Excalibur.zip`, sem versão semântica própria publicada. O alvo físico atual é Simply More `1.3.0_alpha`.

## 1. Papel e authority
Simply More x Excalibur é uma camada visual não oficial que adapta armas de Simply More à estética Excalibur. **Simply More** continua authority de weapon registry, stats, abilities, recipes e comportamento em combate.

## 2. Cobertura confirmada
A publicação oficial confirma cobertura das espadas-base de **iron, gold, diamond, netherite e runic**. Sem inventário binário do ZIP, o catálogo não extrapola essa declaração para todo item ou arma adicionada pelo mod.

## 3. Versão e distribuição
O arquivo oficial usa o nome `Simply More x Excalibur.zip` em múltiplas versões de Minecraft e não publica número semântico próprio. Por isso `Versão 1.21.1` permanece vazia; não se transforma a versão do jogo em versão do resource pack.

## 4. Stack físico e boundary com Simply Swords
O target é Simply More `1.3.0_alpha`. `Simply Swords x Excalibur` é outro resource pack e cobre outro mod; os dois não devem ser confundidos nem usados como evidência um do outro.

## 5. Load order e reload
Para prevalecer visualmente, o pack deve ficar acima de Excalibur/base assets e de qualquer retexture concorrente que se queira substituir. Resource reload/relog deve alterar somente textures/models.

## 6. Riscos
1. Simply More `1.3.0_alpha` possuir arma/path posterior ao ZIP.
2. Cobertura publicada não representar 100% do mod.
3. Outro pack de Simply More sobrescrever os mesmos assets.
4. Animações de combate alterarem transforms em mão.
5. Resource reload manter model/texture stale.

## 7. Matriz de testes
- [ ] Conferir espadas iron, gold, diamond, netherite e runic.
- [ ] Amostrar armas adicionais do Simply More e identificar fallbacks.
- [ ] Testar primeira/terceira pessoa com animações de combate ativas.
- [ ] Confirmar prioridade relativa a outros retextures.
- [ ] Resource reload sem missing model/texture.
- [ ] Confirmar que pack on/off não altera stats, abilities ou recipes.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma o propósito visual, o filename sem versão semântica e as espadas-base publicadas. Não foi feito inventário asset por asset do ZIP.

> Boundary canônico: **Simply More controla armas e gameplay; este pack controla apenas os assets visuais que fornece**.
