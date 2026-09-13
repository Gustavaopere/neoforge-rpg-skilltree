# Supplementaries Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db817d8b01e9d41b74c8d4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Data Pack, Addon
- **Arquivo:** `Supplementaries Compat 1.1.zip`
- **Versão 1.21.1:** 1.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra captura física da pasta Data Packs do perfil em 08/09/2026 como evidência de instalação de `Supplementaries Compat 1.1.zip`.
- O alvo principal registrado é Supplementaries `1.21.1-3.9.8`; integrações só são tratadas como ativas quando o mod-alvo correspondente está efetivamente presente.
- O datapack fornece dados de compatibilidade e não assume authority das mecânicas de Supplementaries ou dos mods integrados.

## Propriedades do banco

- **Mod:** Supplementaries Compat
- **Arquivo JAR:** `Supplementaries Compat 1.1.zip`
- **Tipo de conteúdo:** Data Pack, Addon
- **Versão 1.21.1:** 1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat
- **Função:** Datapack de compatibilidade que amplia interoperabilidade do Supplementaries com itens de outros mods, incluindo soups/cookies em jars e bricks modded tratáveis como arremessáveis quando cobertos.
- **Dependências:** Supplementaries 1.21.1-3.9.8 + mods suportados quando presentes. Farmer's Delight está no stack e o upstream o cita como requisito/contexto de integração; Alex's Mobs Continued, Alex's Delight e Oh The Biomes We've Gone também estão fisicamente presentes.
- **Sobreposição:** Pode disputar tags/recipes/data de Supplementaries e integrações com outros datapacks. Supplementaries e mods-alvo permanecem authorities das mecânicas; o pack só fornece dados de compatibilidade.
- **Compatibilidade/Riscos:** Compatibilidade depende dos IDs/tags dos mods suportados. v1.1 corrige cookie tag do mod-base e líquidos de soups. Pode conflitar com outros datapacks que sobrescrevam as mesmas tags/recipes/data. Só integra mods realmente presentes.
- **Observações:** Arquivo físico `Supplementaries Compat 1.1.zip`, versão 1.1, release oficial para 1.21.1. A v1.1 corrige substituição da cookie tag do mod-base e funcionamento de soup liquids.
- **Procedência:** CurseForge oficial Supplementaries Compat 1.1 por CyberRat2 + captura Data Packs do perfil em 08/09/2026 + modlist física Supplementaries 3.9.8 e alvos relevantes.
- **Fonte:** https://www.curseforge.com/minecraft/data-packs/supplementaries-compat/files/8314815
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Supplementaries Compat 1.1, soups/cookies/jars, throwable bricks, targets físicos, datapack lifecycle, overlap, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** —

# Dossiê operacional — padrão Alex's Mobs

> **Datapack físico confirmado no dossiê de origem:** `Supplementaries Compat 1.1.zip`, versão `1.1`, release oficial para Minecraft 1.21.1.

## 1. Papel e authority
Supplementaries Compat adiciona dados de interoperabilidade entre **Supplementaries** e mods suportados. Supplementaries continua authority de jars, bricks e demais mecânicas; os mods-alvo continuam authorities dos próprios itens.

## 2. Cobertura publicada
O upstream documenta compatibilidade para **soups e cookies de mods em jars** e para **bricks de mods como throwable** quando suportados. A lista de projetos suportados é ampla, mas cada integração só é ativa quando o alvo está realmente instalado.

## 3. Stack físico relevante
O perfil contém Supplementaries `1.21.1-3.9.8`, Farmer's Delight e vários alvos publicados como Alex's Mobs Continued, Alex's Delight e Oh The Biomes We've Gone. A ficha não presume presença de qualquer outro mod apenas porque aparece na lista oficial.

## 4. Release 1.1
O changelog registra correção da cookie tag do mod-base e correção dos líquidos de soups. Esses detalhes são dados de compatibilidade; não transferem autoridade das mecânicas ao datapack.

## 5. Lifecycle de datapack
Alterações exigem recarga apropriada de datapacks/reentrada/restart conforme o ambiente. Tags e recipes atualizados passam a ser consumidos pelo runtime após a carga de dados.

## 6. Sobreposição e riscos
1. Outro datapack sobrescrever as mesmas tags/recipes.
2. IDs de itens mudarem após update de um alvo.
3. Mod suportado no upstream não estar instalado.
4. Cookies/soups caírem em comportamento parcial por tag incompatível.
5. Reload incompleto manter dados anteriores.

## 7. Matriz de testes
- [ ] Confirmar datapack habilitado.
- [ ] Testar soup suportada em jar quando aplicável.
- [ ] Testar cookie suportado sem substituir tag base.
- [ ] Testar brick modded arremessável quando coberto.
- [ ] Validar somente integrações de mods realmente presentes.
- [ ] Verificar logs por IDs/tags ausentes.
- [ ] Confirmar que remover o pack não altera conteúdo-base dos mods.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v1.1, 1.21.1, natureza de datapack e as categorias de integração publicadas. A ficha permanece fail-closed para mods/itens não confirmados.

> Boundary canônico: **Supplementaries e os mods-alvo controlam gameplay; o datapack controla somente os dados de compatibilidade que fornece**.
