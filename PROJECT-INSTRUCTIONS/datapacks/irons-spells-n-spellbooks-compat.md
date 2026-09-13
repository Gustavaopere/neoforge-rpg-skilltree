# Iron's Spells 'n Spellbooks Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81ff8434d69c40823489
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Data Pack, Addon
- **Arquivo:** `Irons_Spellbooks_Compat_1.0.0.zip`
- **Versão 1.21.1:** 1.0.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Irons_Spellbooks_Compat_1.0.0.zip` como fisicamente confirmado por captura de Data Packs do perfil em 08/09/2026.
- A modlist física acessível de 08/09/2026 confirma `irons_spellbooks-1.21.1-3.16.3.jar`, mod id `irons_spellbooks`, runtime `1.21.1-3.16.3`; `Quark-4.1-483.jar`, mod id `quark`, runtime `4.1-483`; e `alexscaves-1.0.9-neoforge+1.21.1.jar`, mod id `alexscaves`, runtime `1.0.9`, nome de runtime Alex's Caves Continued.
- Twigs e Undergarden não aparecem na modlist física. Entradas do datapack voltadas a esses providers não são classificadas como integrações ativas.
- O upstream nomeia Alex's Caves original. O fork Continued preserva `alexscaves`, tornando compatibilidade plausível por IDs, mas não upstream-validada; permanece fail-closed para QA.

## Propriedades do banco

- **Mod:** Iron's Spells 'n Spellbooks Compat
- **Arquivo JAR:** `Irons_Spellbooks_Compat_1.0.0.zip`
- **Tipo de conteúdo:** Data Pack, Addon
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Magia
- **Função:** Datapack de compatibilidade para Iron's Spells 'n Spellbooks cuja função principal publicada é ampliar os blocos de chão reconhecidos pelo spell Spectral Hammer para conteúdo de mods suportados.
- **Dependências:** Iron's Spells 'n Spellbooks 3.16.3. Dos alvos publicados, Quark 4.1-483 e Alex's Caves Continued 1.0.9 estão presentes; Twigs e Undergarden não estão presentes. O upstream nomeia Alex's Caves original, portanto o fork Continued requer QA apesar de preservar o mod id `alexscaves`.
- **Sobreposição:** Pode disputar tags/dados usados para classificar blocos válidos do Spectral Hammer com outros datapacks de compatibilidade. Iron's continua authority do spell, dano e casting; mods-alvo continuam authorities dos blocos.
- **Compatibilidade/Riscos:** Compat depende de tags/IDs de ground blocks dos mods suportados. Quark está fisicamente presente. Alex's Caves Continued preserva `alexscaves`, mas não há validação upstream específica do fork. Entradas referentes a Twigs/Undergarden são inertes/irrelevantes enquanto esses mods estiverem ausentes.
- **Observações:** Arquivo físico `Irons_Spellbooks_Compat_1.0.0.zip`, versão 1.0.0, publicado para 1.21.1. Upstream lista Twigs, Undergarden, Alex's Caves e Quark; apenas os alvos fisicamente presentes devem ser considerados ativos.
- **Procedência:** CurseForge oficial Iron's Spells 'n Spellbooks Compat 1.0.0 por CyberRat2 + captura Data Packs do perfil em 08/09/2026 + modlist física Iron's 3.16.3, Quark 4.1-483 e Alex's Caves Continued 1.0.9.
- **Fonte:** https://www.curseforge.com/minecraft/data-packs/irons-spells-n-spellbooks-compat/files/7502025
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Iron's Spellbooks Compat 1.0.0, Spectral Hammer ground blocks, targets físicos Quark/Alex's Caves Continued, target gaps Twigs/Undergarden, fork boundary, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Datapack físico confirmado:** `Irons_Spellbooks_Compat_1.0.0.zip`, versão `1.0.0`, release oficial para Minecraft 1.21.1. O alvo principal é Iron's Spells 'n Spellbooks `3.16.3`.

## 1. Papel e authority
Iron's Spells 'n Spellbooks Compat é um datapack de interoperabilidade. A função principal publicada é tornar o spell **Spectral Hammer** compatível com mais ground blocks de mods suportados. Iron's continua authority do spell, casting, damage, mana e demais mecânicas.

## 2. Mods suportados pelo upstream
O projeto lista **Twigs**, **Undergarden**, **Alex's Caves** e **Quark**. Essa lista descreve alvos suportados pelo datapack, não a presença no perfil.

## 3. Presença física atual
No perfil atual, **Quark 4.1-483** está presente. **Alex's Caves Continued 1.0.9** também está presente e preserva o mod id `alexscaves`; porém o upstream nomeia Alex's Caves original, então a equivalência do fork deve ser validada em runtime. **Twigs e Undergarden não estão presentes** e não são tratados como integrações ativas.

## 4. Boundary funcional
O datapack pode ampliar tags/dados de blocos reconhecidos pelo Spectral Hammer. Ele não cria o spell, não altera sua escola, mana, cooldown ou damage por authority própria, e não passa a controlar os blocos dos mods-alvo.

## 5. Lifecycle de datapack
É conteúdo data-driven e deve estar habilitado no mundo correto. Alterações de tags/dados exigem reload/reentrada/restart conforme o ambiente. Falhas de compat devem degradar a seleção de blocos, não transferir authority de gameplay ao datapack.

## 6. Sobreposição e riscos
1. Outro datapack substituir as mesmas tags/dados de ground blocks.
2. Quark alterar IDs/tags após update.
3. Alex's Caves Continued divergir do original apesar de preservar `alexscaves`.
4. Entradas para Twigs/Undergarden permanecerem sem alvo instalado.
5. Reload parcial manter dados anteriores até nova carga.

## 7. Matriz de testes
- [ ] Confirmar datapack habilitado no mundo.
- [ ] Testar Spectral Hammer em ground blocks do Quark cobertos.
- [ ] Testar Alex's Caves Continued e observar logs por IDs/tags ausentes.
- [ ] Confirmar que ausência de Twigs/Undergarden não gera erros fatais.
- [ ] Verificar prioridade contra outros datapacks de compatibilidade.
- [ ] Reload/restart sem erros de datapack.
- [ ] Confirmar que remover o compat não altera spell registry ou conteúdo dos mods.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v1.0.0, Minecraft 1.21.1, natureza de datapack, função principal do Spectral Hammer e os quatro mods suportados. O catálogo limita integrações ativas aos alvos fisicamente presentes e mantém o fork Alex's Caves Continued fail-closed para validação específica.

> Boundary canônico: **Iron's controla o Spectral Hammer e a magia; os mods-alvo controlam seus blocos; este datapack fornece somente dados de compatibilidade entre eles**.
