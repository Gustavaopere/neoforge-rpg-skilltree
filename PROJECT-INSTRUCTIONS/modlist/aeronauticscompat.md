# AeronauticsCompat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c59bf1c2e01850426d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AeronauticsCompat
- **Arquivo JAR:** `aeronauticscompat-1.1.3.jar`
- **Versão 1.21.1:** `1.1.3`
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — conditional mixin coverage, Sable authority, target-presence rule e Bits 'n' Bobs 2.3.2 reconciliados no QC global #14.
- **Categoria:** Compat; QoL
- **Compatibilidade/Riscos:** Patches podem ficar obsoletos após updates dos mods alvo. Alex's Caves/Mobs corrigidos upstream são ports de Raguto; o pack usa Continued de CodxIO, portanto não assumir cobertura sem validar targets. Storage Drawers mantém bug visual conhecido mesmo com fix upstream.
- **Decisão:** Sem decisão
- **Dependências:** Sable é o requisito técnico central; Create Aeronautics não é tecnicamente obrigatório segundo o autor. Mods alvo são condicionais. No pack atual, Create Bits 'n' Bobs 2.3.2 é alvo confirmado presente.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-compatability
- **Função:** Pacote de mixins/patches que corrige mods incompatíveis com physics-enabled ships do Sable; cobre coordenadas, interação, áudio, pathfinding, montagem e outros casos específicos por mod alvo.
- **Histórico da decisão:** vazio
- **Observações:** Não promover lista upstream inteira a integrações ativas: presença do mod alvo e compatibilidade de classes devem ser verificadas. Camera Mod/Storage Drawers/PneumaticCraft/Immersive Paintings não aparecem como top-level atuais.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Create Aeronautics: Compatibility 1.1.3 + dossiê técnico existente.
- **Sobreposição:** O suporte de câmera 1.1.3 mira Camera Mod de henkelmax; não é evidência de redundância com Aeronautics Camera Sync 1.4.0. Bits 'n' Bobs é cobertura efetivamente relevante no pack atual.
- **Data da última decisão:** vazio

## Escopo e papel
Pacote geral de patches para mods que não se comportam corretamente em **physics-enabled ships do Sable**. O próprio autor esclarece que o requisito técnico central é Sable, não Create Aeronautics em si: o projeto corrige integrações de mods que não usam Sable Companion ou assumem coordenadas/mundo estático.

## Runtime e autoridade
- JAR físico: `aeronauticscompat-1.1.3.jar`.
- Mod ID: `aeronauticscompat`.
- Runtime: `1.1.3`.
- Release oficial NeoForge 1.21.1 de 10/07/2026.

## Cobertura upstream documentada
A página oficial lista patches para Etched, WATERFrAMES, Alex's Mobs (port não oficial de Raguto), Alex's Caves (port não oficial de Raguto), Another Furniture, Immersive Paintings, Thick Air, Create: Bits 'n' Bobs, PneumaticCraft: Repressurized, Sleep Tight, Cobblemon, Storage Drawers e Camera Mod de henkelmax. A 1.1.3 acrescentou suporte de câmera e Storage Drawers e atualizou a versão de Sable.

## Relações com o pack atual
- `Create Bits 'n' Bobs 2.3.2` está presente e é um alvo upstream explícito; o patch documentado evita duplicação de chains ao montar/desmontar ships.
- O pack possui `Aeronautics Camera Sync 1.4.0`, mas **não foi encontrado Camera Mod de henkelmax**. Portanto o “camera support” do AeronauticsCompat não deve ser tratado como duplicata do Camera Sync: são alvos diferentes.
- O pack usa **Alex's Caves Continued** e **Alex's Mobs Continued**, não os ports de Raguto citados pela página. Não há base suficiente para afirmar que os mixins específicos de Alex se aplicam às builds Continued; isso precisa ser validado por classe/target/runtime.
- Não foram encontrados top-level de Storage Drawers, PneumaticCraft ou Immersive Paintings no snapshot atual.

## Compatibilidade, sobreposição e riscos
Por ser um conjunto de mixins condicionais, o risco maior é patch obsoleto após atualização do mod alvo ou mudança de classes. Fixes não devem ser considerados necessários apenas porque o projeto os lista; a presença do alvo e a versão precisam ser confirmadas. O patch de Storage Drawers declara corrigir crash, mas deixa bug visual conhecido.

## Limites
Não é um framework de física, não substitui Sable Companion e não adiciona conteúdo de gameplay autônomo. Também não transforma automaticamente qualquer mod em compatível com ships.

## Testes recomendados
1. Bits 'n' Bobs: montar/desmontar ships com chains e confirmar ausência de duplicação.
2. Verificar log de mixins para targets opcionais ausentes sem erro fatal.
3. Testar qualquer mod alvo que seja adicionado futuramente dentro de ship móvel, incluindo save/reload.
4. Para Alex's Continued, validar explicitamente se os mixins carregam/aplicam antes de atribuir cobertura.
5. Dedicated-server smoke para detectar classloading indevido de patches client-only.

## Evidências
- [CurseForge oficial — Create Aeronautics: Compatibility](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-compatability)
- Modlist física atual e guia consolidado de Tecnologia.
