# Pufferfish's Unofficial Additions

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db8182b5c4e4b38c1d3292
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `pufferfish_unofficial_additions-1.21.1-2.2.8.jar`, mod id `pufferfish_unofficial_additions`, runtime `2.2.8`; Pufferfish's Skills 0.19.0 e Iron's Spells 'n Spellbooks presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Pufferfish's Unofficial Additions 2.2.8, Pufferfish's Skills 0.19.0 e Iron's Spells 'n Spellbooks estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Pufferfish's Unofficial Additions
- **Arquivo JAR:** `pufferfish_unofficial_additions-1.21.1-2.2.8.jar`
- **Versão 1.21.1:** 2.2.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG, Compat
- **Função:** Addon de Pufferfish's Skills que acrescenta fontes de experiência, recompensas, operações de configuração e integrações adicionais para árvores de habilidades.
- **Dependências:** Pufferfish's Skills 0.19.0 é Required Content. Iron's Spells 'n Spellbooks é integração opcional e está presente no pack.
- **Sobreposição:** Extensão do Pufferfish's Skills; não é segundo framework de árvore. A integração com Iron's adiciona fontes/recompensas, sem assumir ownership do sistema de spells.
- **Compatibilidade/Riscos:** Addon server-side/singleplayer. Riscos: XP farming por crops/spells contínuos, duplicate triggers, effect-loading/config drift e integração Iron's. Atributos próprios antigos foram movidos para Additional Attributes; não substitui Pufferfish's Attributes.
- **Observações:** Release 2.2.8 para NeoForge 1.21–1.21.1. Delta exato: fix de crash potencial relacionado ao carregamento de effects.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + publicação/documentação oficial Pufferfish's Unofficial Additions 2.2.8.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/pufferfishs-unofficial-additions
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Pufferfish's Unofficial Additions 2.2.8 reconstruído: addon de Skills, XP por harvest/spell casting, rewards/effects, Iron's Spells opcional, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `pufferfish_unofficial_additions-1.21.1-2.2.8.jar`, mod id `pufferfish_unofficial_additions`, versão `2.2.8`, NeoForge 1.21.1. É um **addon de Pufferfish's Skills**, não uma árvore/sistema RPG independente. A publicação exige Pufferfish's Skills e oferece integração opcional com Iron's Spells 'n Spellbooks, ambos presentes no pack.

## 1. Identidade e papel
- **Mod:** Pufferfish's Unofficial Additions.
- **JAR:** `pufferfish_unofficial_additions-1.21.1-2.2.8.jar`.
- **Mod id:** `pufferfish_unofficial_additions`.
- **Runtime:** `2.2.8`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente publicado:** Server-side / singleplayer.
- **Papel:** ampliar Pufferfish's Skills com novas fontes de experiência, recompensas/operações e integrações.
- **Decisão:** Sem decisão.

## 2. Dependência de Pufferfish's Skills
A publicação oficial declara **Pufferfish's Skills como Required Content**. Este addon não fornece o framework-base: categorias, árvores, nós, pontos e persistência continuam sob Pufferfish's Skills.

Isso cria causalidade inversa importante no catálogo: enquanto este addon permanecer instalado, Pufferfish's Skills é load-bearing.

## 3. Fontes adicionais de experiência
O projeto adiciona fontes que podem alimentar árvores de skills. Um exemplo documentado é experiência por **harvest de crops**, com operações/condições sobre jogador, bloco, ferramenta, seeds e crops dropados.

A quantidade e a árvore que recebe XP dependem da configuração; não foram inferidas do simples fato de o addon estar instalado.

## 4. Integração com Iron's Spells
Iron's Spells 'n Spellbooks é integração opcional publicada e está presente fisicamente no pack. O addon oferece fonte de experiência por **spell casting**.

Spells contínuos podem disparar por tick; a configuração inclui conceito de `expected_ticks` para normalizar/ponderar esse comportamento. Esse ponto é crítico para evitar farm de XP por spells canalizados.

## 5. Recompensas e operações
O addon acrescenta operações/recompensas complementares ao sistema de Skills. Elas permanecem data/config-driven: a API existir não significa que uma árvore concreta do pack esteja usando cada operação.

Toda árvore custom deve ser auditada por ID de reward/source e custo real.

## 6. Attributes movidos para outro projeto
A documentação oficial informa que atributos próprios antigos do addon foram movidos para **Additional Attributes**.

Consequência: não tratar este addon como substituto de Pufferfish's Attributes nem presumir que instalar Unofficial Additions registre automaticamente os atributos antigos.

## 7. Release 2.2.8
A build 2.2.8 é Release para NeoForge 1.21–1.21.1. O delta exato publicado corrige **um crash potencial relacionado ao carregamento de effects**.

Regression gate: árvores/rewards que referenciam effects válidos, ausentes ou opcionais devem carregar de modo previsível, sem reproduzir o crash corrigido.

## 8. Lineage relevante
A 2.2.7 já havia corrigido um possível crash com Iron's Spells e tornado `show_icon` opcional em effect rewards. Esses itens são lineage e servem como testes de regressão; não são apresentados como novos deltas exclusivos de 2.2.8.

## 9. Client/server e autoridade
Pontos, XP, desbloqueios e rewards precisam permanecer server-authoritative. O cliente pode exibir feedback, mas não deve conseguir conceder XP apenas reproduzindo animação/cast local.

A publicação server-side reforça que o núcleo funcional deve ser validado no servidor integrado/dedicado conforme a árvore usada.

## 10. Lifecycle e configuração
Testar cold boot, datapack/config reload quando suportado, login/reconnect, reset de árvore, spell contínuo interrompido, colheita automatizada/manual e provider opcional ausente.

Fontes não devem duplicar listeners após reload nem conceder XP duas vezes pelo mesmo evento lógico.

## 11. Riscos
1. **XP farming:** crops/spells contínuos podem gerar progressão excessiva.
2. **Effect loading:** superfície corrigida em 2.2.8.
3. **Iron's integration drift:** mudança de spell/cast API.
4. **Duplicate triggers:** mais de uma fonte observa o mesmo evento.
5. **Config drift:** IDs de rewards/sources mudam entre versões.
6. **Wrong attribute assumption:** atributos antigos foram movidos para outro mod.
7. **Server authority:** cliente não pode criar XP/reward funcional sozinho.

## 12. Matriz de testes
- [ ] Dedicated server/singleplayer inicia com addon 2.2.8 + Skills 0.19.0.
- [ ] Árvore que usa fonte extra carrega sem configuração inválida.
- [ ] Harvest de crop concede XP uma única vez conforme config.
- [ ] Seeds/crops/tool conditions filtram corretamente.
- [ ] Spell normal concede XP conforme regra configurada.
- [ ] Spell contínuo respeita `expected_ticks`/limites e não farma XP indevidamente.
- [ ] Reward de effect não reproduz crash de loading corrigido.
- [ ] Iron's ausente em cópia de teste não quebra conteúdo que não depende dele.
- [ ] Reset/relog não duplica reward ou listener.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites
- Modlist física: JAR, mod id/runtime e mixin config exatos.
- Publicação oficial: Release 2.2.8 para NeoForge 1.21–1.21.1; Required Content = Pufferfish's Skills; Iron's Spells opcional.
- Documentação oficial: fontes adicionais, harvest crops, spell casting e migração de atributos para Additional Attributes.
- Changelog 2.2.8: correção de crash potencial ao carregar effects.
- **Limite:** árvores/datapacks/configs locais do pack não foram abertos nesta etapa; nenhuma fonte/reward específica foi declarada ativa sem evidência.
