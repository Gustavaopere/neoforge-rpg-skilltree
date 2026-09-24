# EMF Compat: Iron's Spells

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#249**: JAR `emf_compat_iron_spells_1.21.1_2.0.0.jar`, mod id `emf_compat_iron_spells`, runtime `2.0.0`, SHA-1 `515b545870fce128bbf01a0ccacdd19566ed3b22`.

## Propriedades do registro

- **Mod:** EMF Compat: Iron's Spells
- **Arquivo JAR:** `emf_compat_iron_spells_1.21.1_2.0.0.jar`
- **Versão 1.21.1:** `2.0.0`
- **Categoria:** Compat, Visual, Magia
- **Função:** Compat client-side que preserva a pose de conjuração do Iron's Spells em modelos de jogador animados por EMF, capturando os dois braços enquanto cabeça/corpo/pernas permanecem sob EMF.
- **Dependências:** Iron's Spells 'n Spellbooks 3.16.3 + Entity Model Features 3.3.5 + Entity Texture Features 7.2.1 + EMF Compat Core 2.0.0 no runtime físico.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos de arms pose ser sobrescrita/duplicada, head/body/legs indevidamente capturados, first/third mismatch, outro animation mod competir pelos braços e Core/EMF/ISS version drift. A listagem oficial atual do CurseForge coincide com o artefato físico 2.0.0.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/emf-compat-irons-spells-n-spellbooks
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `emf_compat_iron_spells_1.21.1_2.0.0.jar`, mod id `emf_compat_iron_spells`, runtime 2.0.0 e SHA-1 `515b545870fce128bbf01a0ccacdd19566ed3b22`. CurseForge oficial revalidado em 21/09/2026 lista 2.0.0 como latest release para NeoForge 1.21.1.
- **Observações:** Client-only. Casting preserva os dois braços enquanto head/body/legs permanecem sob EMF. O artefato físico 2.0.0 é confirmado por filename/runtime/SHA-1 e pela listagem oficial atual do CurseForge; a divergência pública registrada no ciclo anterior foi encerrada.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #248: `emf_compat_iron_spells_1.21.1_2.0.0.jar` / runtime `2.0.0` reconfirmados na modlist física atual de 587 entradas top-level incluindo o modloader. A antiga divergência de indexação pública foi encerrada: CurseForge agora lista 2.0.0 como latest release para 1.21.1.
- **Decisão:** Manter
- **Sobreposição:** Complementa ISS + EMF/ETF e atua apenas na pose de cast. Não substitui EFIS: EFIS usa Epic Fight animation/cancel logic; EMF Compat preserva pose contra override EMF. As duas bridges tocam a mesma superfície visual e precisam QA conjunto.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `emf_compat_iron_spells_1.21.1_2.0.0.jar` · mod id `emf_compat_iron_spells` · versão física `2.0.0` · NeoForge 1.21.1 · **Client-only**.
## 1. Papel no modpack
EMF Compat: Iron's Spells preserva as poses de conjuração do **Iron's Spells 'n Spellbooks** quando o jogador usa modelos/animações de **Entity Model Features**. Sem a bridge, a animation do resource pack pode manter os braços em idle e visualmente apagar o gesto de cast.
## 2. Authority / ownership
- **Iron's Spells:** cast state, spell, mana/cooldown e efeitos.
- **EMF:** animation do player model/resource pack.
- **EMF Compat Core:** pose capture/restore framework.
- **EMF Compat: Iron's Spells:** seleção da pose de cast e das partes corporais preservadas.
O mod não decide se o spell foi válido nem executa spell effects.
## 3. Covered pose
A documentação oficial é específica:
- **Casting a spell → Both arms**.
Cabeça, corpo e pernas permanecem sob controle do EMF. Essa boundary é central: capturar mais partes que o necessário reduziria a animação do resource pack e poderia conflitar com movement/pose layers.
## 4. Outros jogadores
O projeto declara que funciona para **other players too**. Assim, um cliente deve enxergar a pose de cast de outro player sem precisar transformar a bridge em authority server-side; o cast state já vem do provider normal e a correção é aplicada localmente no render.
## 5. Resource packs testados upstream
O autor cita testes com:
- Fresh Animations: Player Extension;
- Detailed Animations.
Também espera funcionamento com outros packs de player animation via EMF. Isso é expectativa upstream, não garantia universal; o pack real precisa QA com sua composição visual.
## 6. Runtime local
A modlist física contém:
- Iron's Spells `3.16.3`;
- EMF `3.3.5`;
- ETF `7.2.1`;
- EMF Compat Core `2.0.0`;
- este consumer `2.0.0`.
A linha física está alinhada entre Core e consumer.
## 7. Divergência do índice público
A listagem oficial atual do CurseForge exibe `emf_compat_iron_spells_1.21.1_2.0.0.jar` como latest release para NeoForge 1.21.1, alinhada ao runtime físico `2.0.0`. A divergência pública registrada no ciclo anterior está encerrada.
## 8. Relação com EFIS
O pack também possui **Epic Fight x Iron's Spells: Enhanced Animations 3.1.0**.
- EFIS injeta/coordena animations via Epic Fight e pode cancelar cast por skills.
- EMF Compat: Iron's Spells impede que EMF apague a pose dos braços.
Os dois não são duplicatas integrais, mas tocam a mesma superfície de braço/cast e devem ser testados juntos para evitar transforms concorrentes.
## 9. Client-only boundary
A página oficial classifica o mod como **Client**. Dedicated server não deve depender dele para spell casting. Remover a bridge de um cliente pode degradar a pose visual, mas não deve alterar spell logic, mana, cooldown ou damage no servidor.
## 10. Lifecycle
Validar:
- cast start/end;
- cast interrupt;
- spell wheel/selection → cast;
- first/third person;
- outro jogador casting;
- respawn;
- reconnect;
- dimension change;
- resource reload;
- troca de player animation pack;
- update de ISS/EMF/Core.
## 11. Multiplayer
Cada cliente restaura a pose dos braços com base no state normal recebido. O head/body/legs continuam animados pelo EMF local. Um cliente não deve enviar mutation gameplay por causa da pose.
## 12. Riscos
1. braços permanecerem em idle durante cast;
2. arms transform aplicado duas vezes;
3. head/body/legs serem capturados indevidamente;
4. first/third person divergirem;
5. EFIS e EMF Compat disputarem os braços;
6. animation ficar presa após cancel;
7. Core/consumer version mismatch;
8. ISS mudar pose hooks;
9. EMF mudar animation phase;
10. resource reload deixar cache/pose stale;
11. regredir para 1.0.0 com base em cache/listagem pública desatualizada.
## 13. Matriz de testes
1. Cliente com ISS 3.16.3 + EMF 3.3.5 + ETF 7.2.1 + Core/consumer 2.0.0.
2. Cast em third person e observar both arms.
3. Verificar head/body/legs continuando animation EMF.
4. Outro jogador observar o caster.
5. First-person cast.
6. Cast + cancel/interrupt.
7. Testar junto a EFIS/Epic Fight.
8. Resource reload.
9. Relog/respawn/dimension travel.
10. Trocar player animation pack em instância de teste.
**Esta catalogação não afirma que esses testes foram executados.**
## 14. Evidências
- modlist física atual: JAR/mod id/version + stack ISS/EMF/ETF/Core;
- página oficial: Client-only, objetivo, both-arm capture, head/body/legs EMF, other-player support;
- CurseForge oficial: `emf_compat_iron_spells_1.21.1_2.0.0.jar` como latest release NeoForge 1.21.1.
> **Boundary canônico:** esta bridge corrige somente a **pose visual de cast contra override EMF**. Iron's Spells continua authority do spell.
