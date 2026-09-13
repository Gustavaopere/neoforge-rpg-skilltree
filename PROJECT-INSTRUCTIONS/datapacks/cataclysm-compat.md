# Cataclysm Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81219f6fc0ba655f5a7a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Data Pack, Addon
- **Arquivo:** `CataclysmCompat1.0.zip`
- **Versão 1.21.1:** 1.0
- **Data da exportação:** 2026-09-11

## Autoridade física e evidências na exportação

- O dossiê Notion registra uma **captura física da pasta Data Packs do perfil em 08/09/2026** com `CataclysmCompat1.0.zip` instalado.
- A Biblioteca contém também `minecraftinstance.json` anterior registrando `CataclysmCompat1.0.zip` como habilitado em `datapacks`, além de logs de agosto em que o jogo detecta/carrega o pack. Essas evidências confirmam identidade e instalação histórica, mas não substituem a captura de 08/09 citada pelo dossiê como estado mais recente do `.zip`.
- A modlist física JAR-centric de 08/09 confirma o provider-alvo L_Ender's Cataclysm `3.33`, mas não é por si só inventário de datapacks.

## Propriedades do banco

- **Mod:** Cataclysm Compat
- **Arquivo JAR:** `CataclysmCompat1.0.zip`
- **Tipo de conteúdo:** Data Pack, Addon
- **Versão 1.21.1:** 1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Mobs
- **Função:** Datapack de compatibilidade para L_Ender's Cataclysm que amplia tags/regras de interoperabilidade, incluindo blocos quebráveis por bosses e itens modded tratados como sticky quando suportados.
- **Dependências:** L_Ender's Cataclysm 3.33. Integrações adicionais só têm efeito quando o respectivo mod suportado está presente; Alex's Mobs Continued está fisicamente presente, mas outros alvos não são presumidos.
- **Sobreposição:** Pode disputar as mesmas tags de Cataclysm com outros datapacks. Cataclysm permanece authority de bosses, AI, attacks, damage, loot e estruturas; este pack só complementa dados de compatibilidade.
- **Compatibilidade/Riscos:** Compat depende de IDs/tags dos mods suportados e da versão atual de Cataclysm. Pode se sobrepor a outros datapacks que alterem boss-breakable ou sticky-item tags. Suporte upstream não implica que todos os mods listados estejam instalados.
- **Observações:** Arquivo instalado `CataclysmCompat1.0.zip`, versão 1.0 para 1.21.1. O projeto lista compatibilidades com vários mods; somente alvos fisicamente confirmados devem ser tratados como ativos.
- **Procedência:** CurseForge oficial Cataclysm Compat 1.0 + captura Data Packs do perfil em 08/09/2026 + modlist física L_Ender's Cataclysm 3.33.
- **Fonte:** https://www.curseforge.com/minecraft/data-packs/cataclysm-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Cataclysm Compat 1.0, Cataclysm 3.33, boss-break/sticky tags, loader-agnostic datapack, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Data pack físico confirmado no dossiê de origem:** `CataclysmCompat1.0.zip`, versão `1.0`, Release para Minecraft 1.21.1. O alvo físico atual é L_Ender's Cataclysm `3.33`.

## 1. Papel e authority
Cataclysm Compat complementa dados de interoperabilidade de L_Ender's Cataclysm com outros mods. **Cataclysm** continua authority de bosses, AI, attacks, damage, loot, structures e demais mecânicas.

## 2. Cobertura publicada
O upstream documenta exemplos como adicionar **mais blocos que bosses podem quebrar** e reconhecer **itens modded que devem ser tratados como sticky**. Isso caracteriza o pack como camada de tags/dados de compatibilidade, não como expansão de boss gameplay.

## 3. Mods suportados versus presença real
A lista oficial inclui Twigs, Habitat, Autumnity, Alex's Mobs, Endergetic Expansion, Infernal Expansion e Dungeon Now Loading. **Suporte upstream não significa presença no perfil.** Alex's Mobs Continued está fisicamente presente; os demais só devem ser considerados ativos após confirmação própria.

## 4. Stack físico
O target é L_Ender's Cataclysm `3.33`. Atualizações de item/block IDs ou tags do Cataclysm e dos mods suportados podem tornar entradas do datapack obsoletas ou incompletas.

## 5. Lifecycle de datapack
É conteúdo de dados loader-agnostic. Alterações exigem datapack reload/reentrada/restart conforme o contexto do mundo. Tags atualizadas afetam as consultas de runtime que as consomem; não transferem authority de AI ou damage para o datapack.

## 6. Sobreposição e riscos
1. Outro datapack substituir as mesmas tags.
2. IDs de blocos/itens mudarem após update.
3. Mod listado pelo upstream não estar instalado.
4. Boss-breakability ampliar destruição de cenário de modo indesejado.
5. Reload incompleto manter tags anteriores até nova carga.

## 7. Matriz de testes
- [ ] Confirmar datapack habilitado no mundo.
- [ ] Testar bloco suportado sendo quebrado pelo boss correspondente quando aplicável.
- [ ] Testar comportamento de item sticky coberto.
- [ ] Validar apenas integrações de mods realmente presentes.
- [ ] Verificar logs por IDs/tags ausentes.
- [ ] Reload/restart mantendo Cataclysm funcional.
- [ ] Confirmar que remover o datapack não altera AI/damage base dos bosses.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v1.0 para 1.21.1, natureza loader-agnostic e exemplos de boss-breakable/sticky compatibility. O catálogo não presume instalação de todos os mods suportados.

> Boundary canônico: **Cataclysm controla bosses e gameplay; Cataclysm Compat controla somente tags/dados de interoperabilidade que fornece**.
